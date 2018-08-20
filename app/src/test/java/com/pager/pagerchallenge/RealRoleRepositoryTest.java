package com.pager.pagerchallenge;

import com.pager.pagerchallenge.TeamService.TeamResponse;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.List;
import java.util.Map;
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
public class RealRoleRepositoryTest {

  private RealRolesRepository repository;

  @Mock
  private RolesService service;

  @Mock
  private Map<String, String> roles;

  private MemoryCache<Map<String, String>> cache;

  private TestSubscriber<Map<String, String>> testSubscriber;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    testSubscriber = new TestSubscriber<>();
    cache = new MemoryCache<>();
    repository = new RealRolesRepository(service, cache);
  }

  @Test
  public void testRequestTeam() {
    Flowable<Map<String, String>> success = Flowable.just(roles);
    Mockito.when(service.roles()).thenReturn(success);
    Mockito.when(roles.size()).thenReturn(10);
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(roles);
  }

  @Test
  public void testRequestTeamWithCache() {
    Mockito.when(service.roles()).thenReturn(Flowable.never());
    Mockito.when(roles.size()).thenReturn(10);
    cache.save(roles);
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(roles);
  }

  @Test
  public void testRequestTeamWithCacheAndServiceException() {
    Mockito.when(service.roles()).thenReturn(Flowable.error(new Exception()));
    Mockito.when(roles.size()).thenReturn(10);
    cache.save(roles);
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertValue(roles);
  }

  @Test
  public void testRequestTeamWithNpCacheAndServiceException() {
    Exception exception = new Exception();
    Mockito.when(service.roles()).thenReturn(Flowable.error(exception));
    repository.get().subscribe(testSubscriber);
    testSubscriber.assertError(exception);
  }
}