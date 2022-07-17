package com.example.demo.services.wantedToAchieve;

import com.example.demo.services.wantedToAchieve.events.ImagesUploadedToCloudEvent;
import com.example.demo.services.wantedToAchieve.events.ProfileEditRequestEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudService {
    private final ApplicationEventPublisher eventPublisher;

    public CloudService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(ProfileEditRequestEvent.class)
    public void uploadToCloud(ProfileEditRequestEvent event) {
        MultipartFile coverImageFile = event.getCoverImageFile();
        MultipartFile profileImageFile = event.getProfileImageFile();
        //try to upload the files and return the file URL ... but ... how to return it? Fire new event i guess..
        String coverImageUrl = "";
        String profileImageUrl = "";

        this.fireUploadedToCloudEvent(coverImageUrl, profileImageUrl);
    }

    private void fireUploadedToCloudEvent(String coverImageUrl, String profileImageUrl) {
        this.eventPublisher.publishEvent(new ImagesUploadedToCloudEvent(
                CloudService.class.getSimpleName(),
                coverImageUrl,
                profileImageUrl)
        );
    }
}
