package com.pager.pagerchallenge;

import io.reactivex.Completable;

public class ComposedTeamMateInteractor implements TeamMateUpdateInteractor {

  private final RealEventsRepository socket;

  public ComposedTeamMateInteractor(RealEventsRepository socket) {
    this.socket = socket;
  }

  @Override
  public Completable send(String status) {
    return socket.send(status);
  }
}
