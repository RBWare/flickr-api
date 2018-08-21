package com.rbware.flickrapi.activity;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rbware.flickrapi.FlickrApplication;
import com.rbware.flickrapi.network.model.FlickrPhoto;
import com.rbware.flickrapi.ui.FlickrImageAdapter;
import com.rbware.flickrapi.network.model.FlickrResult;
import com.rbware.flickrapi.R;
import com.rbware.flickrapi.network.api.FlickrApi;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements FlickrImageAdapter.ItemClickListener {

    @Inject
    Retrofit mFlickrService;

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFABSearch;
    private ArrayList<FlickrPhoto> mPhotoList;

    private static final String DEFAULT_SEARCH_TERM = "ice hockey"; // Not sure what else to use for this.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((FlickrApplication) getApplication()).getNetComponent().inject(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        int numberOfColumns = 3;
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));


        mFABSearch = (FloatingActionButton)findViewById(R.id.fab_search);

        mFABSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultPhotos();
    }

    @Override
    public void onItemClick(View view, int position) {


        // TODO - time running out.
//        String title = ;
//        int imageWidth;
//        int imageHeight;
//        int imageFileSize;
//
//
//
//        final String photoInformation =
//                "Title: " + mPhotoList.get(position).getTitle();
//
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Image Information")
//                .setMessage("Title: ")
//                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Hide dialog :)
//                    }
//                })
//                .show();
    }

    private void getDefaultPhotos() {
        FlickrApi flickrApi = mFlickrService.create(FlickrApi.class);
        Call<FlickrResult> call = flickrApi.getPhotos(25, DEFAULT_SEARCH_TERM);

        call.enqueue(new Callback<FlickrResult>() {
            @Override
            public void onResponse(Call<FlickrResult> callback, Response<FlickrResult> response) {

                if (response.isSuccessful()){
                    final FlickrResult photoList = response.body();

                    mPhotoList = photoList.photos.photoList;

                    FlickrImageAdapter adapter = new FlickrImageAdapter(MainActivity.this,
                            photoList.photos.photoList);

                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<FlickrResult> call, Throwable t) {
                Log.wtf(getClass().getSimpleName(), t.toString());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
