package com.rbware.flickrapi.activity;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.rbware.flickrapi.FlickrApplication;
import com.rbware.flickrapi.R;
import com.rbware.flickrapi.network.api.FlickrApi;
import com.rbware.flickrapi.network.model.FlickrResult;
import com.rbware.flickrapi.ui.FlickrImageAdapter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener, FlickrImageAdapter.ItemClickListener  {

    @Inject
    Retrofit mFlickrService;

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private ArrayList mPhotoList;

    private final int MAX_ITEMS_PER_PAGE = 25; // Limiting results for now, since no maximum was specified.

    private SearchView.SearchAutoComplete mSearchAutoComplete; // TODO - possible improvement: save old results and display here.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ((FlickrApplication) getApplication()).getNetComponent().inject(this);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        mEmptyView = (TextView)findViewById(R.id.emptyView);

        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.search_flickr));

        mSearchMenuItem.expandActionView();

        mSearchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mSearchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(((TextView)view).getText(), true);
            }
        });
        mSearchAutoComplete.setTextColor(Color.WHITE);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                finish(); // Not onBackPressed, since there can be weird behavior with the search bar
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        search(query);
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        // TODO - gallery view?
    }

    private void search(String query) {
        FlickrApi flickrApi = mFlickrService.create(FlickrApi.class);
        Call<FlickrResult> call = flickrApi.getPhotos(MAX_ITEMS_PER_PAGE, query);

        call.enqueue(new Callback<FlickrResult>() {
            @Override
            public void onResponse(Call<FlickrResult> callback, Response<FlickrResult> response) {

                if (response.isSuccessful()){
                    final FlickrResult photoList = response.body();

                    if (photoList != null && photoList.photos != null
                            && photoList.photos.photoList != null
                            && !photoList.photos.photoList.isEmpty()) {

                        mEmptyView.setVisibility(View.GONE);
                        mPhotoList = photoList.photos.photoList;

                        FlickrImageAdapter adapter = new FlickrImageAdapter(SearchActivity.this,
                                photoList.photos.photoList);

                        mRecyclerView.setAdapter(adapter);
                    }  else {
                        mEmptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setAdapter(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<FlickrResult> call, Throwable t) {
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(null);
                Log.wtf(getClass().getSimpleName(), t.toString()); // Heh, wtf :)
            }
        });
    }
}
