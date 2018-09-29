package com.pager.pagerchallenge.main.presenter.result;

import com.pager.pagerchallenge.network.model.TeamMember;

import java.util.List;

//TODO possibly change to builder pattern
final public class TeamMembersResult extends MainResult {
    boolean inProgress = false;
    boolean success = false;
    String errorMessage = null;
    boolean isError;
    public List<TeamMember> teamMembers;

    public TeamMembersResult(final boolean inProgress, final boolean success,
                             final String errorMessage, final boolean isError,
                             final List<TeamMember> teamMembers) {
        this.inProgress = inProgress;
        this.success = success;
        this.errorMessage = errorMessage;
        this.isError = isError;
        this.teamMembers = teamMembers;
    }

    static TeamMembersResult idle() {
        return new TeamMembersResult(false, false, null, false, null);
    }

    static TeamMembersResult inProgress() {
        return new TeamMembersResult(true, false, null, false, null);
    }

    public static TeamMembersResult success(List<TeamMember> teamMembers) {
        return new TeamMembersResult(false, true, null, false, teamMembers);
    }

    public static TeamMembersResult failure(String errorMessage) {
        return new TeamMembersResult(false, false, errorMessage, true, null);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TeamMembersResult) {
            TeamMembersResult teamMembersResults = (TeamMembersResult) obj;
            return inProgress == teamMembersResults.inProgress
                    && success == teamMembersResults.success
                    && isError == teamMembersResults.isError;
        }
        return false;
    }
}