package com.pager.pagerchallenge.network.repository;

import com.pager.pagerchallenge.network.model.TeamMember;

import java.io.IOException;
import java.util.List;

public interface ITeamRepository {

  List<TeamMember> all() throws IOException;

  void add(TeamMember user);

  TeamMember findByGithub(String user);


}