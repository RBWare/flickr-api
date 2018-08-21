package com.rbware.flickrapi.network.model;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlickrPhotos{

    public int page;
    public String pages;
    public int perpage;
    public String total;
    @SerializedName("photo")
    public ArrayList<FlickrPhoto> photoList;
    public String stat;



}