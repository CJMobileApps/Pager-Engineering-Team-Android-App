package com.pager.pagerchallenge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;

final class RealTeamRepository implements TeamRepository {

  private final TeamService service;

  private List<Member> team;

  RealTeamRepository(Retrofit retrofit) {
    this(retrofit.create(TeamService.class));
  }

  RealTeamRepository(TeamService service) {
    this.service = service;
  }

  @Override
  public List<Member> all() throws IOException {
    retrofit2.Response<List<TeamService.TeamResponse>> response = service.team().execute();
    if (response.isSuccessful()) {
      List<TeamService.TeamResponse> body = response.body();
      if (body != null) {
        team = map(body);
        return team;
      }
    }
    throw new IOException(response.errorBody().string());
  }

  @Override
  public void add(Member member) {
    team.add(member);
  }

  @Override
  public Member findByGithub(String user) {
    for (Member member : team) {
      if (member.github().equals(user)) {
        return member;
      }
    }
    throw new IllegalArgumentException();
  }

  private List<Member> map(List<TeamService.TeamResponse> body) {
    List<Member> result = new ArrayList<>(body.size());
    for (TeamService.TeamResponse element : body) {
      result.add(map(element));
    }
    return result;
  }

  private Member map(TeamService.TeamResponse element) {
    return new Member(element.name(), element.avatar(), element.github(), element.languages(),
      element.tags(), element.location(), element.role());
  }
}
