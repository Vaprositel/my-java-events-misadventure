package com.example.demo.services;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import com.example.demo.models.dtos.UserLoginDTO;
import com.example.demo.models.dtos.UserProfileEditDTO;
import com.example.demo.models.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final CloudService cloudService;

    public UserService(UserRepository userRepository, AuthenticationService authenticationService, CloudService cloudService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.cloudService = cloudService;
    }

    public AuthenticatedLoginDTO editUserProfile(Long userId,
                                                 UserProfileEditDTO dto,
                                                 MultipartFile profileImageFile,
                                                 MultipartFile coverImageFile,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        UserEntity oldUserInfo = this.userRepository.findById(userId).orElseThrow();

        this.authenticationService.handleInvalidPassword(dto.getPassword(), oldUserInfo.getPassword());

        boolean userWithTheSameUsernameOrEmailExists =
                this.otherUserWithSameUsernameOrEmailExists(dto, oldUserInfo);

        if (userWithTheSameUsernameOrEmailExists) {
            throw new RuntimeException("Exists already ...");
        }

        this.uploadCoverAndAvatarImagesToTheCloudProviderIfSuchFilesAreProvidedAndSetThemAsDTOImageUrl(
                dto,
                profileImageFile,
                coverImageFile
        );

        this.setDefaultValuesForImagesIfNoImageUrlsAreProvided(dto);

        UserEntity editedUser = new UserEntity(); //this.modelMapper.map(dto, UserEntity.class);

        this.userRepository.save(editedUser);

        this.authenticationService.logout(request, response);

        UserLoginDTO loginDTO = new UserLoginDTO(); //this.modelMapper.map(editedUser, UserLoginDTO.class);

        return this.authenticationService.login(loginDTO);
    }

    private void setDefaultValuesForImagesIfNoImageUrlsAreProvided(UserProfileEditDTO dto) {
        if (dto.getAvatarUrl().isBlank()) {
            dto.setAvatarUrl("something");
        }
        //does the same with the cover img
    }

    private void uploadCoverAndAvatarImagesToTheCloudProviderIfSuchFilesAreProvidedAndSetThemAsDTOImageUrl(
            UserProfileEditDTO dto, MultipartFile profileImageFile, MultipartFile coverImageFile) {
        if (!profileImageFile.isEmpty()) {
            String uploadedURL = this.cloudService.uploadToCloud(profileImageFile);
            dto.setAvatarUrl(uploadedURL);
        }

        //does the same with the cover image file...
    }

    private boolean otherUserWithSameUsernameOrEmailExists(UserProfileEditDTO dto, UserEntity oldUserInfo) {
        //uses user repository to check for OTHER user with the same username or email, that the current user is
        //attending to take
        return false;
    }
}
