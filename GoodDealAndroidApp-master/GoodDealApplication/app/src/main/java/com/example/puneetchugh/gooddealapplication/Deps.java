package com.example.puneetchugh.gooddealapplication;

import com.example.puneetchugh.gooddealapplication.activity.SearchActivity;
import com.example.puneetchugh.gooddealapplication.networkservices.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by puneetchugh on 1/30/18.
 */

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(SearchActivity searchActivity);
}
