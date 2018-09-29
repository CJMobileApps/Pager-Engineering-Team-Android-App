package com.pager.pagerchallenge.main.presenter.result;

import com.pager.pagerchallenge.network.model.User;

import java.util.List;


final public class NewStatusOrNewUserResult extends MainResult {
    boolean inProgress = false;
    boolean success = false;
    String errorMessage = null;
    boolean isError;
    public List<User> userList;

    public NewStatusOrNewUserResult(final boolean inProgress, final boolean success,
                      final String errorMessage, final boolean isError,
                      final List<User> userList) {
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.isError = isError;
        this.userList = userList;
    }

    static NewStatusOrNewUserResult idle() {
        return new NewStatusOrNewUserResult(false, false, null, false, null);
    }

    public static NewStatusOrNewUserResult inProgress() {
        return new NewStatusOrNewUserResult(true, false, null, false, null);
    }

    public static NewStatusOrNewUserResult success(List<User> userList) {
        return new NewStatusOrNewUserResult(false, true, null, false, userList);
    }

    public static NewStatusOrNewUserResult failure(String errorMessage) {
        return new NewStatusOrNewUserResult(false, false, errorMessage, true, null);
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NewStatusOrNewUserResult) {
            NewStatusOrNewUserResult newStatusOrNewUserResult = (NewStatusOrNewUserResult) obj;
            return inProgress == newStatusOrNewUserResult.inProgress
                    && success == newStatusOrNewUserResult.success
                    && isError == newStatusOrNewUserResult.isError;
        }
        return false;
    }
}