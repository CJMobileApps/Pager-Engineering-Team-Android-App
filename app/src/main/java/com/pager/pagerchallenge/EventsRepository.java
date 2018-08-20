package com.pager.pagerchallenge;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface EventsRepository {

  Flowable<String> get();

  Completable send(final String message);
}
