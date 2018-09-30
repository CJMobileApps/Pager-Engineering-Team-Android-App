package com.pager.pagerchallenge.dagger.module;

import android.app.Application;
import android.content.Context;

import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context mContext;

    public ContextModule(Application application) {
        this.mContext = application.getApplicationContext();
    }

    @PagerChallengeAndroidApplicationScope
    @Provides
    public Context context() {
        return mContext;
    }
}
