package com.pager.pagerchallenge;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.pager.pagerchallenge.dagger.DaggerPagerChallengeAndroidApplicationComponent;
import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationComponent;
import com.pager.pagerchallenge.dagger.module.ContextModule;


public class PagerChallengeAndroidApplication extends Application {

    private PagerChallengeAndroidApplicationComponent mPagerChallengeAndroidApplicationComponent;

    public static PagerChallengeAndroidApplication get(Activity activity) {
        return (PagerChallengeAndroidApplication) activity.getApplication();
    }

    public static PagerChallengeAndroidApplication get(Context context) {
        return (PagerChallengeAndroidApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mPagerChallengeAndroidApplicationComponent = DaggerPagerChallengeAndroidApplicationComponent.builder()
                .contextModule(new ContextModule(this)).build();

        mPagerChallengeAndroidApplicationComponent.injectPagerChallengeAndroidApplicationComponent(this);
    }

    public PagerChallengeAndroidApplicationComponent getPagerChallengeAndroidApplicationComponent() {
        return mPagerChallengeAndroidApplicationComponent;
    }
}
