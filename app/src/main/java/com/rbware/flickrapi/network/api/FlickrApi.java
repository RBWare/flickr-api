package com.rbware.flickrapi.network.api;

import com.rbware.flickrapi.network.model.FlickrResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

public interface FlickrApi {

    @GET("?method=flickr.photos.search&api_key=949e98778755d1982f537d56236bbb42&nojsoncallback=1&format=json")
    Call<FlickrResult> getPhotos(@Query("per_page") int perPage, @Query("tags") String query);
}
