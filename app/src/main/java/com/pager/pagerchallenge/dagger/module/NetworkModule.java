package com.pager.pagerchallenge.dagger.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.pager.pagerchallenge.network.HttpRolesRepository;
import com.pager.pagerchallenge.network.RealGetUsersUseCase;
import com.pager.pagerchallenge.network.RealTeamRepository;
import com.pager.pagerchallenge.network.SocketRepository;
import com.pager.pagerchallenge.dagger.PagerChallengeAndroidApplicationScope;
import com.pager.pagerchallenge.network.NetworkService;
import com.squareup.moshi.Moshi;

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
    public RealGetUsersUseCase realGetUsersUseCase(Retrofit retrofit) {
        return new RealGetUsersUseCase(new HttpRolesRepository(retrofit), new RealTeamRepository(retrofit),
                new SocketRepository("http://ios-hiring-backend.dokku.canillitapp.com", new OkHttpClient(),
                        new Moshi.Builder().build()));
    }

    @PagerChallengeAndroidApplicationScope
    @Provides
    public NetworkService networkService(RealGetUsersUseCase realGetUsersUseCase) {
        return new NetworkService(realGetUsersUseCase);
    }
}