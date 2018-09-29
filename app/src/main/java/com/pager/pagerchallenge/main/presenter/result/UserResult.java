package com.pager.pagerchallenge.main.presenter.result;

import com.pager.pagerchallenge.network.model.User;

import java.util.List;
import java.util.Map;


final public class UserResult extends MainResult {
    boolean inProgress = false;
    boolean success = false;
    String errorMessage = null;
    boolean isError;
    public List<User> userList;
    public Map<Integer, String> rolesMap;

    public UserResult(final boolean inProgress, final boolean success,
                      final String errorMessage, final boolean isError,
                      final List<User> userList,
                      final  Map<Integer, String> rolesMap) {
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.isError = isError;
        this.userList = userList;
        this.rolesMap = rolesMap;
    }

    static UserResult idle() {
        return new UserResult(false, false, null, false, null, null);
    }

    public static UserResult inProgress() {
        return new UserResult(true, false, null, false, null, null);
    }

    public static UserResult success(List<User> userList, Map<Integer, String> rolesMap) {
        return new UserResult(false, true, null, false, userList, rolesMap);
    }

    static UserResult failure(String errorMessage) {
        return new UserResult(false, false, errorMessage, true, null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserResult) {
            UserResult userResult = (UserResult) obj;
            return inProgress == userResult.inProgress
                    && success == userResult.success
                    && isError == userResult.isError;
        }
        return false;
    }
}