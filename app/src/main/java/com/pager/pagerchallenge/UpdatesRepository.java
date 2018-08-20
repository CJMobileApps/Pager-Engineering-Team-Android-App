package com.pager.pagerchallenge;

import io.reactivex.Flowable;

public interface UpdatesRepository {

  Flowable<TeamEvent> get();
}
