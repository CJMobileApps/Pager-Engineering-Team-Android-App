package com.pager.pagerchallenge;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;

interface RolesService {

  @GET("/roles")
  Call<Map<String, String>> roles();
}