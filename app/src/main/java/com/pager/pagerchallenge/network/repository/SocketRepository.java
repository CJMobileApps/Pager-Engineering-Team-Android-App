package com.pager.pagerchallenge.network.repository;

import com.squareup.moshi.Json;
import com.squareup.moshi.Moshi;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public final class SocketRepository implements IEventsRepository {

  private final String url;

  private final OkHttpClient client;

  private final Moshi moshi;

  public SocketRepository(String url, OkHttpClient client, Moshi moshi) {
    this.url = url;
    this.client = client;
    this.moshi = moshi;
  }

  @Override
  public void connect(Listener listener) {
    client.newWebSocket(new Request.Builder().url(url).build(), new WebSocketListener() {
      @Override
      public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        listener.onEvent(parse(text));
      }
    });
  }

  private Event parse(String text) {
    try {
      final EventStatus statusResponse = statusFrom(text);
      if (isValidStatus(statusResponse)) {
        return statusResponse;
      }
      final EventNewUser newUserResponse = userFrom(text);
      if (isValidUser(newUserResponse)) {
        return newUserResponse;
      }
      throw new IllegalArgumentException("response is not valid");
    } catch (Exception e) {
      throw new IllegalArgumentException("response is not valid");
    }
  }

  private EventStatus statusFrom(final String responseText) {
    try {
      return moshi.adapter(EventStatus.class).fromJson(responseText);
    } catch (Exception e) {
      //deliberated return null to create a NPE
      return null;
    }
  }

  private boolean isValidStatus(final EventStatus status) {
    return status != null && status.user != null && status.state != null && status.event
      .equals("state_change");
  }

  private EventNewUser userFrom(final String responseText) {
    try {
      return moshi.adapter(EventNewUser.class).fromJson(responseText);
    } catch (Exception e) {
      //deliberated return null to create a NPE
      return null;
    }
  }

  private boolean isValidUser(final EventNewUser newUser) {
    return newUser != null && newUser.user != null && newUser.event.equals("user_new");
  }

  public static final class EventStatus implements Event {

    @Json(name = "event")
    private final String event;

    @Json(name = "user")
    private final String user;

    @Json(name = "state")
    private final String state;

    EventStatus(String event, String user, String state) {
      this.event = event;
      this.user = user;
      this.state = state;
    }

    public String event() {
      return event;
    }

    public String user() {
      return user;
    }

    public String state() {
      return state;
    }

    @Override
    public Type type() {
      return Type.CHANGE_STATUS;
    }
  }

  public static final class EventNewUser implements Event {

    @Json(name = "event")
    private final String event;

    @Json(name = "user")
    private final EventUser user;

    EventNewUser(String event, EventUser user) {
      this.event = event;
      this.user = user;
    }

    public String event() {
      return event;
    }

    public EventUser user() {
      return user;
    }

    @Override
    public Type type() {
      return Type.NEW_USER;
    }
  }

  public static final class EventUser {

    @Json(name = "name")
    private final String name;

    @Json(name = "avatar")
    private final String avatar;

    @Json(name = "github")
    private final String github;

    @Json(name = "location")
    private final String location;

    @Json(name = "languages")
    private final List<String> languages;

    @Json(name = "tags")
    private final List<String> tags;

    @Json(name = "role")
    private final Integer role;

    EventUser(String name, String avatar, String github, String location, List<String> languages,
      List<String> tags, Integer role) {
      this.name = name;
      this.avatar = avatar;
      this.github = github;
      this.location = location;
      this.languages = languages;
      this.tags = tags;
      this.role = role;
    }

    public String name() {
      return name;
    }

    public String avatar() {
      return avatar;
    }

    public String github() {
      return github;
    }

    public String location() {
      return location;
    }

    public List<String> languages() {
      return languages;
    }

    public List<String> tags() {
      return tags;
    }

    public Integer role() {
      return role;
    }
  }
}
