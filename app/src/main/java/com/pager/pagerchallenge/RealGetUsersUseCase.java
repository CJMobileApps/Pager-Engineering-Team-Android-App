package com.pager.pagerchallenge;

import com.pager.pagerchallenge.EventsRepository.Type;
import com.pager.pagerchallenge.SocketRepository.EventNewUser;
import com.pager.pagerchallenge.SocketRepository.EventStatus;
import com.pager.pagerchallenge.SocketRepository.EventUser;
import com.pager.pagerchallenge.TeamRepository.Member;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import java.util.List;
import java.util.Map;

final class RealGetUsersUseCase {

  private final RolesRepository rolesRepository;

  private final TeamRepository teamRepository;

  private final EventsRepository eventsRepository;

  RealGetUsersUseCase(RolesRepository rolesRepository, TeamRepository teamRepository,
    EventsRepository eventsRepository) {
    this.rolesRepository = rolesRepository;
    this.teamRepository = teamRepository;
    this.eventsRepository = eventsRepository;
  }

  Flowable<User> exec() {
    return Flowable.create(emitter -> {
      Map<String, String> roles = rolesRepository.all();
      List<Member> team = teamRepository.all();
      for (Member member : team) {
        String role = roleFor(roles, member);
        emitter.onNext(map(member, role, ""));
      }
      eventsRepository.connect(event -> {
        if (event.type() == Type.NEW_USER) {
          EventNewUser userEvent = (EventNewUser) event;
          Member user = map(userEvent.user());
          String role = roleFor(roles, user);
          teamRepository.add(user);
          emitter.onNext(map(user, role, ""));
        } else if (event.type() == Type.CHANGE_STATUS) {
          EventStatus eventStatus = (EventStatus) event;
          Member user = teamRepository.findByGithub(eventStatus.user());
          String role = roleFor(roles, user);
          emitter.onNext(map(user, role, eventStatus.state()));
        }
      });
    }, BackpressureStrategy.LATEST);
  }

  private Member map(EventUser user) {
    return new Member(user.name(), user.avatar(), user.github(), user.languages(), user.tags(),
      user.location(), user.role());
  }

  private User map(Member member, String role, String status) {
    return new User(member.name(), member.languages(), member.skills(), member.location(), status,
      role);
  }

  private String roleFor(Map<String, String> roles, Member member) {
    String key = String.valueOf(member.role());
    if (roles.containsKey(key)) {
      return roles.get(key);
    } else {
      return "";
    }
  }
}