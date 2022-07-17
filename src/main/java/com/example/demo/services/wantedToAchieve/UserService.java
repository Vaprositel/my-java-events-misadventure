package com.example.demo.services.wantedToAchieve;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import com.example.demo.models.dtos.UserLoginDTO;
import com.example.demo.models.dtos.UserProfileEditDTO;
import com.example.demo.models.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AuthenticationService;
import com.example.demo.services.wantedToAchieve.events.ImagesUploadedToCloudEvent;
import com.example.demo.services.wantedToAchieve.events.LoginSuccessEvent;
import com.example.demo.services.wantedToAchieve.events.ProfileEditRequestEvent;
import com.example.demo.services.wantedToAchieve.events.ProfileEditSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public AuthenticatedLoginDTO editUserProfile(Long userId,
                                                 UserProfileEditDTO dto,
                                                 MultipartFile profileImageFile,
                                                 MultipartFile coverImageFile,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {

        UserEntity oldUserInfo = this.userRepository.findById(userId).orElseThrow();

        boolean userWithTheSameUsernameOrEmailExists =
                this.otherUserWithSameUsernameOrEmailExists(dto, oldUserInfo);

        if (userWithTheSameUsernameOrEmailExists) {
            throw new RuntimeException("Exists already ...");
        }

        this.fireProfileEditRequestEvent(dto, oldUserInfo, profileImageFile, coverImageFile);

        this.listenForUploadedPicturesEvent(); //oops .. i have problem with this method, click on it.

        this.setDefaultValuesForImagesIfNoImageUrlsAreProvided(dto);

        UserEntity editedUser = new UserEntity(); //this.modelMapper.map(dto, UserEntity.class);

        this.userRepository.save(editedUser);

        UserLoginDTO loginDTO = new UserLoginDTO(); //this.modelMapper.map(editedUser, UserLoginDTO.class);

        this.fireProfileEditSuccessEvent(request, response, loginDTO);

        return this.listenForLoginSuccessEvent(); //oops .. i have problem with this method again ...
    }

    private void fireProfileEditSuccessEvent(HttpServletRequest request,
                                             HttpServletResponse response,
                                             UserLoginDTO loginDTO) {
        this.eventPublisher.publishEvent(new ProfileEditSuccessEvent(
                UserService.class.getSimpleName(),
                request,
                response,
                loginDTO
        ));

    }

    @EventListener(ImagesUploadedToCloudEvent.class)
    public void listenForUploadedPicturesEvent(ImagesUploadedToCloudEvent event) {
        //WOOHOO, I HAVE SOME IMAGE URLS IN THE EVENT!!!! ... But wait ?? How am i supposed to set them to the
        // dto ??? ? ? ? Ummm ... lucho ? Help ?
    }

    private void fireProfileEditRequestEvent(UserProfileEditDTO dto,
                                             UserEntity oldUserInfo,
                                             MultipartFile profileImageFile,
                                             MultipartFile coverImageFile) {
        ProfileEditRequestEvent profileEditEvent = new ProfileEditRequestEvent(
                UserService.class.getSimpleName(),
                dto,
                oldUserInfo,
                profileImageFile,
                coverImageFile
        );
        this.eventPublisher.publishEvent(profileEditEvent);
    }

    @EventListener(LoginSuccessEvent.class)
    public AuthenticatedLoginDTO listenForLoginSuccessEvent(LoginSuccessEvent event) {
        return event.getAuthenticatedLoginDTO();
    }

    private void setDefaultValuesForImagesIfNoImageUrlsAreProvided(UserProfileEditDTO dto) {
        if (dto.getAvatarUrl().isBlank()) {
            dto.setAvatarUrl("something");
        }
        //does the same with the cover img
    }

    private boolean otherUserWithSameUsernameOrEmailExists(UserProfileEditDTO dto, UserEntity oldUserInfo) {
        //uses user repository to check for OTHER user with the same username or email, that the current user is
        //attending to take
        return false;
    }
}
