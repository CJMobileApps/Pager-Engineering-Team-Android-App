package com.pager.pagerchallenge;

import com.pager.pagerchallenge.network.EventsRepository;
import com.pager.pagerchallenge.network.EventsRepository.Listener;
import com.pager.pagerchallenge.network.SocketRepository.EventNewUser;
import com.pager.pagerchallenge.network.SocketRepository.EventStatus;
import com.pager.pagerchallenge.network.SocketRepository.EventUser;
import com.pager.pagerchallenge.network.TeamRepository;
import com.pager.pagerchallenge.network.TeamRepository.Member;
import com.pager.pagerchallenge.network.RealGetUsersUseCase;
import com.pager.pagerchallenge.network.RolesRepository;
import com.pager.pagerchallenge.network.User;

import io.reactivex.subscribers.TestSubscriber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class RealGetUsersUseCaseTest {

  private static final Map<String, String> ROLES = new HashMap<>();

  private static final User USER_NO_STATUS =
    new User("Miguel Gonzales", Collections.singletonList("English"),
      Collections.singletonList("C++"), "Argentina", "", "C++ Developer");

  private static final User USER_STATUS =
    new User("Miguel Gonzales", Collections.singletonList("English"),
      Collections.singletonList("C++"), "Argentina", "Happy Coding", "C++ Developer");

  private static final List<Member> TEAM = new ArrayList<>();

  private static final Member MEMBER =
    new Member("Miguel Gonzales", "", "magon", Collections.singletonList("English"),
      Collections.singletonList("C++"), "Argentina", 1);

  private static final EventUser EVENT_USER =
    new EventUser("Miguel Gonzales", "", "magon", "Argentina", Collections.singletonList("English"),
      Collections.singletonList("C++"), 1);
  static {
    ROLES.put("1", "C++ Developer");
    TEAM.add(MEMBER);
  }
  @Mock
  private TeamRepository teamRepository;

  @Mock
  private RolesRepository rolesRepository;

  @Mock
  private EventsRepository eventsRepository;

  @Captor
  private ArgumentCaptor<Listener> captor;

  private TestSubscriber<User> subscriber;

  private RealGetUsersUseCase useCase;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    subscriber = new TestSubscriber<>();
    useCase = new RealGetUsersUseCase(rolesRepository, teamRepository, eventsRepository);
  }

  @Test
  public void testReceiveUpdateUser() throws Exception {
    Mockito.when(rolesRepository.all()).thenReturn(ROLES);
    Mockito.when(teamRepository.all()).thenReturn(Collections.EMPTY_LIST);
    useCase.exec().subscribe(subscriber);
    Mockito.verify(eventsRepository).connect(captor.capture());
    captor.getValue().onEvent(new EventNewUser("state_change", EVENT_USER));
    subscriber.assertValue(USER_NO_STATUS);
  }

  @Test
  public void testReceiveStatusUpdate() throws Exception {
    Mockito.when(rolesRepository.all()).thenReturn(ROLES);
    Mockito.when(teamRepository.all()).thenReturn(Collections.EMPTY_LIST);
    Mockito.when(teamRepository.findByGithub(Mockito.anyString())).thenReturn(MEMBER);
    useCase.exec().subscribe(subscriber);
    Mockito.verify(eventsRepository).connect(captor.capture());
    captor.getValue().onEvent(new EventStatus("user_new", "magon", "Happy Coding"));
    subscriber.assertValue(USER_STATUS);
  }

  @Test
  public void testReceiveAllUser() throws Exception {
    Mockito.when(rolesRepository.all()).thenReturn(ROLES);
    Mockito.when(teamRepository.all()).thenReturn(TEAM);
    useCase.exec().subscribe(subscriber);
    subscriber.assertValue(USER_NO_STATUS);
  }

  @Test
  public void testErrorRoles() throws Exception {
    Mockito.when(rolesRepository.all()).thenThrow(RuntimeException.class);
    Mockito.when(teamRepository.all()).thenReturn(TEAM);
    useCase.exec().subscribe(subscriber);
    subscriber.assertError(RuntimeException.class);
  }

  @Test
  public void testErrorTeamDontCrash() throws Exception {
    Mockito.when(rolesRepository.all()).thenReturn(ROLES);
    Mockito.when(teamRepository.all()).thenThrow(RuntimeException.class);
    useCase.exec().subscribe(subscriber);
    subscriber.assertNoValues();
  }
}
