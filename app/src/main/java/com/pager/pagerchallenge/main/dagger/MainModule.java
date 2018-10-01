package com.pager.pagerchallenge.main.dagger;

import com.pager.pagerchallenge.main.presenter.MainPresenter;
import com.pager.pagerchallenge.main.navigator.MainPresenterToViewNavigator;
import com.pager.pagerchallenge.main.view.MainAdapter;
import com.pager.pagerchallenge.network.NetworkService;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainPresenterToViewNavigator mMainPresenterToViewNavigator;

    public MainModule(MainPresenterToViewNavigator mainPresenterToViewNavigator) {
        this.mMainPresenterToViewNavigator = mainPresenterToViewNavigator;
    }

    @MainScope
    @Provides
    public MainPresenterToViewNavigator provideMainPresenterToViewNavigator() {
        return mMainPresenterToViewNavigator;
    }

    @MainScope
    @Provides
    public MainPresenter mainPresenter(MainPresenterToViewNavigator mainPresenterToViewNavigator, NetworkService networkService) {
        return new MainPresenter(mainPresenterToViewNavigator, networkService);
    }

    @MainScope
    @Provides
    public MainAdapter mainAdapter(Picasso picasso) {
        return new MainAdapter(picasso);
    }

}
