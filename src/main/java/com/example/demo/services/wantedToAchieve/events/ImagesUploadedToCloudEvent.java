package com.example.demo.services.wantedToAchieve.events;

import org.springframework.context.ApplicationEvent;

public class ImagesUploadedToCloudEvent extends ApplicationEvent {
    private final String coverImageUrl;
    private final String profileImageUrl;

    public ImagesUploadedToCloudEvent(String simpleName, String coverImageUrl, String profileImageUrl) {
        super(simpleName);
        this.coverImageUrl = coverImageUrl;
        this.profileImageUrl = profileImageUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
