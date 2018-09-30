package com.pager.pagerchallenge.dagger.module;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pager.pagerchallenge.network.NetworkRepositories;
import com.pager.pagerchallenge.network.repository.RolesRepository;
import com.pager.pagerchallenge.network.repository.SocketRepository;
import com.pager.pagerchallenge.network.repository.TeamRepository;
import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationScope;
import com.pager.pagerchallenge.network.NetworkService;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @PagerChallengeAndroidApplicationScope
    @Provides
    public Retrofit retrofit() {
        return new Retrofit.Builder().baseUrl("https://pager-team.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

    }

    @PagerChallengeAndroidApplicationScope
    @Provides
    public OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }

    @PagerChallengeAndroidApplicationScope
    @Provides
    public NetworkRepositories networkRepositories(Retrofit retrofit, OkHttpClient okHttpClient) {
        return new NetworkRepositories(
                new RolesRepository(retrofit),
                new TeamRepository(retrofit),
                new SocketRepository(
                        "http://ios-hiring-backend.dokku.canillitapp.com",
                        okHttpClient,
                        new Moshi.Builder().build()));
    }

    @PagerChallengeAndroidApplicationScope
    @Provides
    public NetworkService networkService(NetworkRepositories networkRepositories) {
        return new NetworkService(networkRepositories);
    }

    //Picasso image loading
    @PagerChallengeAndroidApplicationScope
    @Provides
    public Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }
}