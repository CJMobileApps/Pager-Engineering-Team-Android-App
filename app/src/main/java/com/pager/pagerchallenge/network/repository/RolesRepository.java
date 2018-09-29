package com.pager.pagerchallenge.network.repository;


import com.pager.pagerchallenge.network.service.RolesService;

import java.io.IOException;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RolesRepository implements IRolesRepository {

  private final RolesService service;

  public RolesRepository(Retrofit retrofit) {
    service = retrofit.create(RolesService.class);
  }

  @Override
  public Map<Integer, String> all() throws IOException {
    Response<Map<Integer, String>> response = service.roles().execute();
    if (response.isSuccessful()) {
      return response.body();
    } else {
      throw new RuntimeException(response.errorBody().string());
    }
  }
}
