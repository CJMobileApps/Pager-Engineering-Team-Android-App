package com.pager.pagerchallenge.dagger;

import com.pager.pagerchallenge.PagerChallengeAndroidApplication;
import com.pager.pagerchallenge.dagger.module.NetworkModule;
import com.pager.pagerchallenge.network.NetworkService;

import dagger.Component;

@PagerChallengeAndroidApplicationScope
@Component(modules = {NetworkModule.class})
public interface PagerChallengeAndroidApplicationComponent {

    NetworkService provideNetworkService();

    void injectPagerChallengeAndroidApplicationComponent(PagerChallengeAndroidApplication pagerChallengeAndroidApplication);
}
