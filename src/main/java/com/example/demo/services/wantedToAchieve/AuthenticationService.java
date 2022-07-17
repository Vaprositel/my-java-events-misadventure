package com.example.demo.services.wantedToAchieve;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import com.example.demo.models.dtos.UserLoginDTO;
import com.example.demo.services.wantedToAchieve.events.LoginSuccessEvent;
import com.example.demo.services.wantedToAchieve.events.ProfileEditRequestEvent;
import com.example.demo.services.wantedToAchieve.events.ProfileEditSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
    private final ApplicationEventPublisher eventPublisher;

    public AuthenticationService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener(ProfileEditRequestEvent.class)
    public void handleInvalidPassword(ProfileEditRequestEvent event) {
        String rawPassword = event.getDto().getPassword();
        String encodedPassword = event.getOldUserInfo().getPassword();
        //Password encoder checks if the password is valid, if it's not - throws exception
    }

    @EventListener(ProfileEditSuccessEvent.class)
    public void logout(ProfileEditSuccessEvent event) {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        //uses security context holder & security context logout handler to logout
    }

    @EventListener(ProfileEditSuccessEvent.class)
    public void login(ProfileEditSuccessEvent event) {
        UserLoginDTO loginDTO = event.getLoginDTO();
        //checks the user credentials and maps to dto upon success with the needed from the client parameters
        //return new AuthenticatedLoginDTO();
        // ooooops, but wait ? I need the response value ... time to fire a new event i guess?
        AuthenticatedLoginDTO authenticatedLoginDTO = new AuthenticatedLoginDTO();

        this.eventPublisher.publishEvent(new LoginSuccessEvent(
                AuthenticationService.class.getSimpleName(),
                authenticatedLoginDTO
        ));
    }
}
