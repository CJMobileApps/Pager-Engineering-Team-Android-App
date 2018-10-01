package com.pager.pagerchallenge.network.model;

import java.util.List;

final public class User {

    private String name;

    private final List<String> languages;

    private final List<String> skills;

    private final String location;

    private String status;

    private String role;

    private String avatar;

    private String github;

    public User(String name, List<String> languages, List<String> skills, String location, String status,
                String role, String avatar, String github) {
        this.name = name;
        this.languages = languages;
        this.skills = skills;
        this.location = location;
        this.status = status;
        this.role = role;
        this.avatar = avatar;
        this.github = github;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String role() {
        return role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getGithub() {
        return github;
    }

    public String getLanguages() {
        return languages.toString();
    }

    public String getSkills() {
        return skills.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (!name.equals(user.name)) {
            return false;
        }
        if (!languages.equals(user.languages)) {
            return false;
        }
        if (!skills.equals(user.skills)) {
            return false;
        }
        if (!location.equals(user.location)) {
            return false;
        }
        if (!status.equals(user.status)) {
            return false;
        }

        if (!github.equals(user.github)) {
            return false;
        }

        return role.equals(user.role);
    }


    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + languages.hashCode();
        result = 31 * result + skills.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + github.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", languages=" + languages + ", skills=" + skills
                + ", location='" + location + '\'' + ", status='" + status + '\'' + ", role='" + role + '\''
                + '}';
    }
}