package com.pager.pagerchallenge;

import org.junit.Test;

public class RealGetUsersInteractorTest {

  @Test
  public void testReceiveUser() {
    new RealGetUsersInteractor(teamRepository, updatesRepsitory);
  }

  private class RealGetUsersInteractor {

    private final TeamRepository teamRepository;

    private final EventsRepository eventsRepository;

    RealGetUsersInteractor(TeamRepository teamRepository, EventsRepository eventsRepository) {
      this.teamRepository = teamRepository;
      this.eventsRepository = eventsRepository;
    }
  }
}
