package com.pager.pagerchallenge;

import io.reactivex.Flowable;
import java.util.Map;

public class RealRolesRepository implements RolesRepository {

  private final RolesService service;

  private final Cache<Map<String, String>> cache;

  public RealRolesRepository(RolesService service, Cache<Map<String, String>> cache) {
    this.service = service;
    this.cache = cache;
  }

  @Override
  public Flowable<Map<String, String>> get() {
    return Flowable.concat(cache.get(), service.roles().retryWhen(RxUtils.incremental())).take(1)
      .doOnNext(cache::save);
  }
}
