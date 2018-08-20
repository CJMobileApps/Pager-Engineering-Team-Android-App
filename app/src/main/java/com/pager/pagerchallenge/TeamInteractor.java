package com.pager.pagerchallenge;

import io.reactivex.Flowable;

public interface TeamInteractor {

  Flowable<TeamEvent> users();
}
