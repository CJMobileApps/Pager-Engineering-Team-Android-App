package com.pager.pagerchallenge;

import com.pager.pagerchallenge.TeamEvent.Status;
import com.pager.pagerchallenge.TeamService.TeamResponse;
import com.squareup.moshi.Moshi;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GetUpdatesUseCaseTest {

  private Moshi moshi = new Moshi.Builder().build();

  @Mock
  private EventsRepository eventsRepository;

  @Mock
  private TeamRepository teamRepository;

  @Mock
  private RolesRepository rolesRepository;

  @Mock
  private List<TeamResponse> team;

  @Mock
  private Map<String, String> roles;

  @Mock
  private User user;

  private GetUpdatesUseCase repository;

  private TestSubscriber<TeamEvent> testSubscriber = new TestSubscriber<>();

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    repository = new GetUpdatesUseCase(moshi, eventsRepository, teamRepository, rolesRepository);
  }

  @Test
  public void testNoEvents() {
    Mockito.when(eventsRepository.get()).thenReturn(Flowable.never());
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertNoValues();
  }

  @Test
  public void testSingleEvent() {
    String event = "{\"event\":\"state_change\",\"user\":\"alexruzzarin\",\"state\":\"At a "
      + "meeting \uD83D\uDE26\"}";
    Mockito.when(eventsRepository.get()).thenReturn(Flowable.just(event));
    Mockito.when(teamRepository.get()).thenReturn(Flowable.just(team));
    Mockito.when(rolesRepository.get()).thenReturn(Flowable.just(roles));
    repository.get().subscribe(testSubscriber);
    TeamEvent expected = new TeamEvent.Status(user);
    testSubscriber.assertValue(expected);
  }
}
