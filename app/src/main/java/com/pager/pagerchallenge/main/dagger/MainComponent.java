package com.pager.pagerchallenge.main.dagger;

import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationComponent;
import com.pager.pagerchallenge.main.MainActivity;

import dagger.Component;

@MainScope
@Component(modules = {MainModule.class}, dependencies = PagerChallengeAndroidApplicationComponent.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
