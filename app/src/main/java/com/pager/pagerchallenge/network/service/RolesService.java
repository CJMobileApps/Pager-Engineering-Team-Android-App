package com.pager.pagerchallenge.network.service;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RolesService {

  @GET("/roles")
  Call<Map<Integer, String>> roles();
}