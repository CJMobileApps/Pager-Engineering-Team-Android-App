package com.pager.pagerchallenge.network;

import io.reactivex.Flowable;

public class NetworkService {

        private RealGetUsersUseCase realGetUsersUseCase;

        public NetworkService(RealGetUsersUseCase realGetUsersUseCase) {
            this.realGetUsersUseCase = realGetUsersUseCase;
        }

        public Flowable<User> getRealUsersUseCase() {
            return realGetUsersUseCase.exec();
        }
}
