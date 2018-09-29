package com.pager.pagerchallenge.network;

import com.pager.pagerchallenge.network.model.User;
import com.pager.pagerchallenge.network.model.TeamMember;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class NetworkService {

        private NetworkRepositories networkRepositories;

        public NetworkService(NetworkRepositories networkRepositories) {
            this.networkRepositories = networkRepositories;
        }

        public Single<List<TeamMember>> getTeamSingle() {
            return networkRepositories.getTeamSingle();
        }

        public Single<Map<Integer, String>> getRolesSingle() {
            return networkRepositories.getRolesSingle();
        }

       public Flowable<User> getNewStatusOrNewUser() {
            return networkRepositories.getNewStatusOrNewUser();
       }
}
