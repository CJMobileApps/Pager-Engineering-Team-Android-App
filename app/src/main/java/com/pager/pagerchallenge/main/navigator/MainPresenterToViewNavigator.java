package com.pager.pagerchallenge.main.navigator;


import com.pager.pagerchallenge.network.model.User;

import java.util.List;

public interface MainPresenterToViewNavigator {
    void setUsersToAdapter(List<User> userList);
    void showProgressDialog();
    void dismissProgressDialog();
}
