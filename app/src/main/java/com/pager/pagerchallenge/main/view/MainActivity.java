package com.pager.pagerchallenge.main.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pager.pagerchallenge.PagerChallengeAndroidApplication;
import com.pager.pagerchallenge.R;
import com.pager.pagerchallenge.main.presenter.MainPresenter;
import com.pager.pagerchallenge.network.model.User;
import com.pager.pagerchallenge.main.dagger.DaggerMainComponent;
import com.pager.pagerchallenge.main.dagger.MainComponent;
import com.pager.pagerchallenge.main.dagger.MainModule;
import com.pager.pagerchallenge.main.navigator.MainPresenterToViewNavigator;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements MainPresenterToViewNavigator {

    private ProgressDialog mProgressDialog;

    @Inject
    MainPresenter mMainPresenter;

    @Inject
    MainAdapter mMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PagerChallengeAndroidApplication pagerChallengeAndroidApplication = PagerChallengeAndroidApplication.get(this);

        MainComponent mainComponent = DaggerMainComponent.builder()
                .mainModule(new MainModule(this))
                .pagerChallengeAndroidApplicationComponent(pagerChallengeAndroidApplication.getPagerChallengeAndroidApplicationComponent())
                .build();
        mainComponent.inject(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMainAdapter);
        mMainPresenter.onCreate();
    }

    @Override
    protected void onPause() {
        dismissProgressDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMainPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void setUsersToAdapter(List<User> userList) {
        mMainAdapter.setUsersList(userList);
    }

    //TODO add on resume logic if all ready showing
    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        }
    }
}
