package com.pager.pagerchallenge;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.pager.pagerchallenge.dagger.DaggerPagerChallengeAndroidApplicationComponent;
import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationComponent;


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

        mPagerChallengeAndroidApplicationComponent = DaggerPagerChallengeAndroidApplicationComponent.builder().build();

        mPagerChallengeAndroidApplicationComponent.injectPagerChallengeAndroidApplicationComponent(this);
    }

    public PagerChallengeAndroidApplicationComponent getPagerChallengeAndroidApplicationComponent() {
        return mPagerChallengeAndroidApplicationComponent;
    }

}
