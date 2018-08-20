package com.pager.pagerchallenge;

import io.reactivex.Completable;

public interface TeamMateUpdateInteractor {

  Completable send(String status);
}
