package com.pager.pagerchallenge.main.navigator;

import com.pager.pagerchallenge.network.User;

import java.util.List;

public interface MainPresenterToViewNavigator {
    public void setUsersToAdapter(List<User> mUserList);
}
