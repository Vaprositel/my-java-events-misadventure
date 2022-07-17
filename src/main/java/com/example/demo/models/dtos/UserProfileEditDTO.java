package com.example.demo.models.dtos;

public class UserProfileEditDTO {
    private String username;
    private String password;
    private String email;
    private String avatarUrl;
    private String coverPhotoUrl;

    public UserProfileEditDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }

    public String getPassword() {
        return password;
    }

    public UserProfileEditDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
