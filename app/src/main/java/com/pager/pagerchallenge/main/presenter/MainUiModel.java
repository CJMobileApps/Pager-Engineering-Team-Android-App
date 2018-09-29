package com.pager.pagerchallenge.main.presenter;

import com.pager.pagerchallenge.network.model.User;

import java.util.List;
import java.util.Map;

final class MainUiModel {
    boolean inProgress = false;
    boolean success = false;
    String errorMessage = null;
    List<User> userList;
    boolean newStatusOrNewUser;
    Map<Integer, String> rolesMap;

    private MainUiModel(final boolean inProgress, final boolean success,
                        final String errorMessage, final List<User> userList,
                        final boolean newStatusOrNewUser, final Map<Integer, String> rolesMap) {
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.userList = userList;
        this.newStatusOrNewUser = newStatusOrNewUser;
        this.rolesMap = rolesMap;
    }

    static MainUiModel idle() {
        return new MainUiModel(false, false, null, null, false, null);
    }

    static MainUiModel inProgress() {
        return new MainUiModel(true, false, null, null, false, null);
    }

    static MainUiModel success(List<User> userList, boolean newStatusOrNewUser, Map<Integer, String> rolesMap) {
        return new MainUiModel(false, true, null, userList, newStatusOrNewUser, rolesMap);
    }

    static MainUiModel failure(String errorMessage) {
        return new MainUiModel(false, false, errorMessage, null, false, null);
    }
}