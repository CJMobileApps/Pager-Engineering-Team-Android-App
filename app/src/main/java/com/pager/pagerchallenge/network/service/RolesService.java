package com.pager.pagerchallenge.network.service;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RolesService {

  @GET("/roles")
  Single<Map<Integer, String>> roles();
}