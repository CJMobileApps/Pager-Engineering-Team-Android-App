package com.pager.pagerchallenge.dagger;

import android.content.Context;

import com.pager.pagerchallenge.PagerChallengeAndroidApplication;
import com.pager.pagerchallenge.dagger.module.ContextModule;
import com.pager.pagerchallenge.dagger.module.NetworkModule;
import com.pager.pagerchallenge.network.NetworkService;
import com.squareup.picasso.Picasso;

import dagger.Component;

@PagerChallengeAndroidApplicationScope
@Component(modules = {NetworkModule.class, ContextModule.class})
public interface PagerChallengeAndroidApplicationComponent {

    NetworkService provideNetworkService();

    //Context provideContext();

    Picasso providePicasso();

    void injectPagerChallengeAndroidApplicationComponent(PagerChallengeAndroidApplication pagerChallengeAndroidApplication);
}
