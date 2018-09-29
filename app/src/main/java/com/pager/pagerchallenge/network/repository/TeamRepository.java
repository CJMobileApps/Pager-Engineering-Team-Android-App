package com.pager.pagerchallenge.network.repository;


import com.pager.pagerchallenge.network.model.TeamMember;
import com.pager.pagerchallenge.network.service.TeamService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;

public final class TeamRepository implements ITeamRepository {

  private final TeamService service;

  private List<TeamMember> teamMemberList;

  public TeamRepository(Retrofit retrofit) {
    this(retrofit.create(TeamService.class));
  }

  TeamRepository(TeamService service) {
    this.service = service;
  }

  @Override
  public List<TeamMember> all() throws IOException {
    retrofit2.Response<List<TeamService.TeamResponse>> response = service.team().execute();
    if (response.isSuccessful()) {
      List<TeamService.TeamResponse> body = response.body();
      if (body != null) {
        teamMemberList = map(body);
        return teamMemberList;
      }
    }
    throw new IOException(response.errorBody().string());
  }

  @Override
  public void add(TeamMember teamMember) {
    teamMemberList.add(teamMember);
  }

  @Override
  public TeamMember findByGithub(String user) {
    for (TeamMember teamMember : teamMemberList) {
      if (teamMember.github().equals(user)) {
        return teamMember;
      }
    }
    throw new IllegalArgumentException();
  }

  private List<TeamMember> map(List<TeamService.TeamResponse> body) {
    List<TeamMember> result = new ArrayList<>(body.size());
    for (TeamService.TeamResponse element : body) {
      result.add(map(element));
    }
    return result;
  }

  private TeamMember map(TeamService.TeamResponse element) {
    return new TeamMember(element.name(), element.avatar(), element.github(), element.languages(),
      element.tags(), element.location(), element.role());
  }
}
