package com.pager.pagerchallenge;

import com.pager.pagerchallenge.TeamService.TeamResponse;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RealTeamRepositoryTest {

  private RealTeamRepository repository;

  @Mock
  private TeamService service;

  @Mock
  private List<TeamResponse> team;

  private MemoryCache<List<TeamResponse>> cache;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    cache = new MemoryCache<>();
    repository = new RealTeamRepository(service, cache);
  }

  @Test
  public void testRequestTeam() {
    Flowable<List<TeamResponse>> success = Flowable.just(team);
    Mockito.when(service.team()).thenReturn(success);
    Mockito.when(team.size()).thenReturn(10);
    TestSubscriber<List<TeamResponse>> testSubscriber = new TestSubscriber<>();
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(team);
  }

  @Test
  public void testRequestTeamWithCache() {
    Mockito.when(service.team()).thenReturn(Flowable.never());
    Mockito.when(team.size()).thenReturn(10);
    cache.save(team);
    TestSubscriber<List<TeamResponse>> testSubscriber = new TestSubscriber<>();
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(team);
  }

  @Test
  public void testRequestTeamWithCacheAndServiceException() {
    Mockito.when(service.team()).thenReturn(Flowable.error(new Exception()));
    Mockito.when(team.size()).thenReturn(10);
    cache.save(team);
    TestSubscriber<List<TeamResponse>> testSubscriber = new TestSubscriber<>();
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(team);
  }

  @Test
  public void testRequestTeamWithNpCacheAndServiceException() {
    Exception exception = new Exception();
    Mockito.when(service.team()).thenReturn(Flowable.error(exception));
    TestSubscriber<List<TeamResponse>> testSubscriber = new TestSubscriber<>();
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertError(exception);
  }
}