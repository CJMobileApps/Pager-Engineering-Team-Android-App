package com.pager.pagerchallenge.network.repository;

public interface IEventsRepository {

  void connect(Listener listener);

  enum Type {NEW_USER, CHANGE_STATUS;}

  interface Event {

    Type type();
  }

  interface Listener {

    void onEvent(Event event);
  }
}