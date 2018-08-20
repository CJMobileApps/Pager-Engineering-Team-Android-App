package com.pager.pagerchallenge;

import com.squareup.moshi.Moshi;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetUpdatesUseCase implements UpdatesRepository {

  private final Moshi moshi;

  private final EventsRepository socket;

  private final TeamRepository teamRepository;

  private final RolesRepository rolesRepository;

  public GetUpdatesUseCase(Moshi moshi, EventsRepository socket, TeamRepository teamRepository,
    RolesRepository rolesRepository) {
    this.moshi = moshi;
    this.socket = socket;
    this.teamRepository = teamRepository;
    this.rolesRepository = rolesRepository;
  }

  @Override
  public Flowable<TeamEvent> get() {
    return socket.get().flatMap(msg -> users()
      .flatMap(users -> rolesRepository.get().map(roles -> toEvent(msg, users, roles))));
  }

  private Flowable<List<User>> users() {
    return Flowable.zip(teamRepository.get(), rolesRepository.get(), this::usersOn);
  }

  private TeamEvent toEvent(final String responseText, final List<User> users,
    final Map<String, String> roles) {
    final Object response = responseOn(responseText);
    if (response instanceof StatusResponse) {
      return statusResponseWith(response, users);
    }
    if (response instanceof NewUserResponse) {
      return newUserResponseWith(response, roles);
    }
    throw new IllegalArgumentException();
  }

  private Object responseOn(final String responseText) {
    try {
      final StatusResponse statusResponse = statusFrom(responseText);
      if (isValidStatus(statusResponse)) {
        return statusResponse;
      }
      final NewUserResponse newUserResponse = userFrom(responseText);
      if (isValidUser(newUserResponse)) {
        return newUserResponse;
      }
      throw new IllegalArgumentException("response is not valid");
    } catch (Exception e) {
      throw new IllegalArgumentException("response is not valid");
    }
  }

  private TeamEvent statusResponseWith(Object response, List<User> users) {
    final StatusResponse statusResponse = (StatusResponse) response;
    final User user = findByName(statusResponse.user, users);
    final User newUser = BasicUser
      .create(user.name(), user.avatar(), user.github(), user.role(), user.location(),
        statusResponse.state, user.languages(), user.tags());
    return TeamEvent.Status.with(newUser);
  }

  private TeamEvent newUserResponseWith(Object response, Map<String, String> roles) {
    final UserResponse newUserResponse = ((NewUserResponse) response).user;
    final User newUser = BasicUser
      .create(newUserResponse.name, newUserResponse.avatar, newUserResponse.github,
        roles.get(String.valueOf(newUserResponse.role)), newUserResponse.location, "",
        newUserResponse.languages, newUserResponse.tags);
    return TeamEvent.New.with(newUser);
  }

  private StatusResponse statusFrom(final String responseText) {
    try {
      return moshi.adapter(StatusResponse.class).fromJson(responseText);
    } catch (Exception e) {
      //deliberated return null to create a NPE
      return null;
    }
  }

  private boolean isValidStatus(final StatusResponse statusResponse) {
    return statusResponse != null && statusResponse.user != null && statusResponse.state != null
      && statusResponse.event.equals("state_change");
  }

  private NewUserResponse userFrom(final String responseText) {
    try {
      return moshi.adapter(NewUserResponse.class).fromJson(responseText);
    } catch (Exception e) {
      //deliberated return null to create a NPE
      return null;
    }
  }

  private boolean isValidUser(final NewUserResponse newUserResponse) {
    return newUserResponse != null && newUserResponse.user != null && newUserResponse.event
      .equals("user_new");
  }

  private User findByName(final String userName, final List<User> users) {
    for (User user : users) {
      if (user.github().equals(userName)) { return user; }
    }
    throw new IllegalArgumentException("username not found in users");
  }

  private List<User> usersOn(List<TeamService.TeamResponse> teamResponses,
    Map<String, String> rolesMap) {
    final List<User> users = new ArrayList<>(teamResponses.size());
    for (TeamService.TeamResponse response : teamResponses) {
      users.add(userOf(response, rolesMap.get(String.valueOf(response.role))));
    }
    return users;
  }

  private User userOf(final TeamService.TeamResponse teamResponse, final String role) {
    return BasicUser.create(teamResponse.name, teamResponse.avatar, teamResponse.github, role,
      teamResponse.location, "", teamResponse.languages, teamResponse.tags);
  }

  //Basic DTOs to use with Moshi to parse responses
  private static class StatusResponse {

    String event;

    String user;

    String state;
  }

  private static class NewUserResponse {

    String event;

    UserResponse user;
  }

  private static class UserResponse {

    String name;

    String avatar;

    String github;

    String location;

    List<String> languages, tags;

    Integer role;
  }
}