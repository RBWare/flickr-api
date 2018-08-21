package com.rbware.flickrapi;

import android.app.Application;

import com.rbware.flickrapi.component.ApiComponent;
import com.rbware.flickrapi.component.DaggerApiComponent;
import com.rbware.flickrapi.module.ApiModule;
import com.rbware.flickrapi.module.AppModule;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

public class FlickrApplication extends Application {

    private ApiComponent mApiComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApiComponent = DaggerApiComponent.builder()
                .appModule(new AppModule(this))
                .apiModule(new ApiModule("https://api.flickr.com/services/rest/"))
                .build();
    }

    public ApiComponent getNetComponent() {
        return mApiComponent;
    }
}