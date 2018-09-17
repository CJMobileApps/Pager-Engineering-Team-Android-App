package com.pager.pagerchallenge.main;

import android.util.Log;

import com.pager.pagerchallenge.network.User;
import com.pager.pagerchallenge.main.navigator.MainPresenterToViewNavigator;
import com.pager.pagerchallenge.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter {

    private NetworkService mNetworkService;
    private MainPresenterToViewNavigator mMainPresenterToViewNavigator;
    List<User> userList = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable;

    public MainPresenter(MainPresenterToViewNavigator mainPresenterToViewNavigator, NetworkService networkService) {
        this.mNetworkService = networkService;
        this.mMainPresenterToViewNavigator = mainPresenterToViewNavigator;
    }


    public void onCreate() {

        mCompositeDisposable = new CompositeDisposable();

        loadUsers();
    }

    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    public void loadUsers() {
        mCompositeDisposable.add(getRealGetUsersUseCase());
    }


    //TODO clean up this whole thing here more rxjava and possibly use a set instead
    private Disposable getRealGetUsersUseCase() {
        return mNetworkService.getRealUsersUseCase()
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                user -> {
                                    Log.v("HERE_", user.toString());
                                    boolean isUserInList = false;

                                    for(User currentUser: userList) {
                                        if(user.getName() == currentUser.getName()) {
                                            currentUser.setStatus(user.getStatus());
                                            isUserInList = true;
                                            break;
                                        }
                                    }

                                    if(!isUserInList) {
                                        userList.add(user);
                                    }

                                    mMainPresenterToViewNavigator.setUsersToAdapter(userList);
                                    Log.d("HERE_", "getRealGetUsersUseCase: userSet " + userList.toString());
                                },

                                throwable -> Log.v("Error", throwable.toString()));
    }
}
