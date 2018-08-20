package com.pager.pagerchallenge;

import io.reactivex.Flowable;

public interface Cache<T> {

  Flowable<T> get();

  void save(final T response);
}
