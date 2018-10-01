package com.pager.pagerchallenge.network;

import android.util.Log;

import com.pager.pagerchallenge.network.repository.IEventsRepository;
import com.pager.pagerchallenge.network.repository.IEventsRepository.Type;
import com.pager.pagerchallenge.network.repository.IRolesRepository;
import com.pager.pagerchallenge.network.repository.SocketRepository.EventNewUser;
import com.pager.pagerchallenge.network.repository.SocketRepository.EventStatus;
import com.pager.pagerchallenge.network.repository.SocketRepository.EventUser;
import com.pager.pagerchallenge.network.model.User;
import com.pager.pagerchallenge.network.model.TeamMember;
import com.pager.pagerchallenge.network.repository.ITeamRepository;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;

import java.util.List;
import java.util.Map;

public final class NetworkRepositories {

    private final IRolesRepository iRolesRepository;

    private final ITeamRepository ITeamRepository;

    private final IEventsRepository IEventsRepository;

    public NetworkRepositories(IRolesRepository iRolesRepository, ITeamRepository iTeamRepository,
                               IEventsRepository IEventsRepository) {
        this.iRolesRepository = iRolesRepository;
        this.ITeamRepository = iTeamRepository;
        this.IEventsRepository = IEventsRepository;
    }

    public Single<List<TeamMember>> getTeamSingle() {
        return Single.create(emitter -> {
            try {
                List<TeamMember> team = ITeamRepository.all();
                emitter.onSuccess(team);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<Map<Integer, String>> getRolesSingle() {
        return Single.create(emitter -> {
            try {
                Map<Integer, String> roles = iRolesRepository.all();
                emitter.onSuccess(roles);
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Flowable<User> getNewStatusOrNewUser() {
        return Flowable.create(emitter -> {
            IEventsRepository.connect(event -> {
                //If new user comes in
                if (event.type() == Type.NEW_USER) {
                    EventNewUser userEvent = (EventNewUser) event;
                    TeamMember user = eventUserToTeamMember(userEvent.user());
                    ITeamRepository.add(user);
                    emitter.onNext(teamMemberAndStatusToUser(user, "Not available"));
                //Else if a user status changes
                } else if (event.type() == Type.CHANGE_STATUS) {
                    EventStatus eventStatus = (EventStatus) event;
                    TeamMember user = ITeamRepository.findByGithub(eventStatus.user());
                    emitter.onNext(teamMemberAndStatusToUser(user, eventStatus.state()));
                }
            });
        }, BackpressureStrategy.LATEST);
    }

    private TeamMember eventUserToTeamMember(EventUser user) {
        return new TeamMember(user.name(), user.avatar(), user.github(), user.languages(), user.tags(),
                user.location(), user.role());
    }

    private User teamMemberAndStatusToUser(TeamMember teamMember, String status) {
        return new User(teamMember.name(), teamMember.languages(),
                teamMember.skills(), teamMember.location(),
                status, String.valueOf(teamMember.role()), teamMember.avatar(), teamMember.github());
    }
}