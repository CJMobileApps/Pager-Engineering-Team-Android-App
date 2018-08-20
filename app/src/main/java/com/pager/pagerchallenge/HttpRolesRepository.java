package com.pager.pagerchallenge;

import java.io.IOException;
import java.util.Map;
import retrofit2.Response;
import retrofit2.Retrofit;

final class HttpRolesRepository implements RolesRepository {

  private final RolesService service;

  HttpRolesRepository(Retrofit retrofit) {
    service = retrofit.create(RolesService.class);
  }

  @Override
  public Map<String, String> all() throws IOException {
    Response<Map<String, String>> response = service.roles().execute();
    if (response.isSuccessful()) {
      return response.body();
    } else {
      throw new RuntimeException(response.errorBody().string());
    }
  }
}
