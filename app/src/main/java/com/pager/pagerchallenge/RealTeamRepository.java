package com.pager.pagerchallenge;

import com.pager.pagerchallenge.TeamService.TeamResponse;
import io.reactivex.Flowable;
import java.util.List;

public class RealTeamRepository implements TeamRepository {

  private final TeamService service;

  private final Cache<List<TeamService.TeamResponse>> cache;

  public RealTeamRepository(final TeamService service, Cache<List<TeamResponse>> cache) {
    this.service = service;
    this.cache = cache;
  }

  @Override
  public Flowable<List<TeamService.TeamResponse>> get() {
    return Flowable.concat(cache.get(), service.team().retryWhen(RxUtils.incremental())).take(1)
      .doOnNext(cache::save);
  }
}
