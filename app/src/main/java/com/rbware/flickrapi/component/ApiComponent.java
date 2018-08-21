package com.rbware.flickrapi.component;

import com.rbware.flickrapi.activity.MainActivity;
import com.rbware.flickrapi.activity.SearchActivity;
import com.rbware.flickrapi.module.ApiModule;
import com.rbware.flickrapi.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface ApiComponent {
    void inject(MainActivity activity);
    void inject(SearchActivity activity);
}
