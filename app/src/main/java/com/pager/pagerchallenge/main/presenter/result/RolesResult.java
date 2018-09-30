package com.pager.pagerchallenge.main.presenter.result;

import android.util.Log;

import java.util.Map;

final public class RolesResult extends MainResult {
    boolean inProgress = false;
    boolean success = false;
    String errorMessage = null;
    boolean isError;
    public Map<Integer, String> rolesMap;

    public RolesResult(final boolean inProgress, final boolean success,
                             final String errorMessage, final boolean isError,
                             final Map<Integer, String> rolesMap) {
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.isError = isError;
        this.rolesMap = rolesMap;
    }

    static RolesResult idle() {
        return new RolesResult(false, false, null, false, null);
    }

    static RolesResult inProgress() {
        return new RolesResult(true, false, null, false, null);
    }

    public static RolesResult success(Map<Integer, String> rolesMap) {
        return new RolesResult(false, true, null, false, rolesMap);
    }

    public static RolesResult failure(String errorMessage) {
        Log.d("HERE_", "failure: roles " + errorMessage);
        return new RolesResult(false, false, errorMessage, true, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RolesResult) {
            RolesResult rolesResult = (RolesResult) obj;
            return inProgress == rolesResult.inProgress
                    && success == rolesResult.success
                    && isError == rolesResult.isError;
        }
        return false;
    }
}