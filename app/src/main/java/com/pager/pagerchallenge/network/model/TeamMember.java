package com.pager.pagerchallenge.network.model;

import java.util.List;

public final class TeamMember {

    private final String name;

    private final String avatar;

    private final String github;

    private final List<String> languages;

    private final List<String> skills;

    private final String location;

    private final int role;

    public TeamMember(String name, String avatar, String github, List<String> languages, List<String> skills,
                      String location, int role) {
        this.name = name;
        this.avatar = avatar;
        this.github = github;
        this.languages = languages;
        this.skills = skills;
        this.location = location;
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

    public List<String> languages() {
        return languages;
    }

    public List<String> skills() {
        return skills;
    }

    public String location() {
        return location;
    }

    public int role() {
        return role;
    }
}