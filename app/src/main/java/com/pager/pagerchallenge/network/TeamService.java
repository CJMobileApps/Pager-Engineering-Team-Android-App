package com.pager.pagerchallenge.network;

import com.squareup.moshi.Json;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TeamService {

  @GET("/team")
  Call<List<TeamResponse>> team();

  class TeamResponse {

    @Json(name = "name")
    private final String name;

    @Json(name = "avatar")
    private final String avatar;

    @Json(name = "github")
    private final String github;

    @Json(name = "location")
    private final String location;

    @Json(name = "languages")
    private final List<String> languages;

    @Json(name = "tags")
    private final List<String> tags;

    @Json(name = "role")
    private final Integer role;

    public TeamResponse(String name, String avatar, String github, String location,
      List<String> languages, List<String> tags, Integer role) {
      this.name = name;
      this.avatar = avatar;
      this.github = github;
      this.location = location;
      this.languages = languages;
      this.tags = tags;
      this.role = role;
    }

    public String name() {
      return name;
    }

    public String avatar() {
      return avatar;
    }

    public String github() {
      return github;
    }

    public String location() {
      return location;
    }

    public List<String> languages() {
      return languages;
    }

    public List<String> tags() {
      return tags;
    }

    public Integer role() {
      return role;
    }
  }
}