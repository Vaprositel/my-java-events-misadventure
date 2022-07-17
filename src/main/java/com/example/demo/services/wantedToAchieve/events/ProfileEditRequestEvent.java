package com.example.demo.services.wantedToAchieve.events;

import com.example.demo.models.dtos.UserProfileEditDTO;
import com.example.demo.models.entities.UserEntity;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

public class ProfileEditRequestEvent extends ApplicationEvent {
    private final UserProfileEditDTO dto;
    private final UserEntity oldUserInfo;
    private final MultipartFile profileImageFile;
    private final MultipartFile coverImageFile;

    public ProfileEditRequestEvent(Object source, UserProfileEditDTO dto, UserEntity oldUserInfo, MultipartFile profileImageFile, MultipartFile coverImageFile) {
        super(source);
        this.dto = dto;
        this.oldUserInfo = oldUserInfo;
        this.profileImageFile = profileImageFile;
        this.coverImageFile = coverImageFile;
    }

    public UserProfileEditDTO getDto() {
        return dto;
    }

    public UserEntity getOldUserInfo() {
        return oldUserInfo;
    }

    public MultipartFile getProfileImageFile() {
        return profileImageFile;
    }

    public MultipartFile getCoverImageFile() {
        return coverImageFile;
    }
}
