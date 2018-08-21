package com.rbware.flickrapi.network.model;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

import com.google.gson.annotations.SerializedName;

public class FlickrResult {

    @SerializedName("photos")
    public FlickrPhotos photos;
}

