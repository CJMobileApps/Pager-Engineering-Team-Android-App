package com.pager.pagerchallenge;

import io.reactivex.Flowable;
import java.util.Map;
import retrofit2.http.GET;

interface RolesService {

  @GET("/roles")
  Flowable<Map<String, String>> roles();
}