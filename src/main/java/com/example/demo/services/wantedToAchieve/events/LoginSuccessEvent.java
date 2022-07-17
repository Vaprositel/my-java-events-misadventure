package com.example.demo.services.wantedToAchieve.events;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import org.springframework.context.ApplicationEvent;

public class LoginSuccessEvent extends ApplicationEvent {
    private final AuthenticatedLoginDTO authenticatedLoginDTO;

    public LoginSuccessEvent(String simpleName, AuthenticatedLoginDTO authenticatedLoginDTO) {
        super(simpleName);
        this.authenticatedLoginDTO = authenticatedLoginDTO;
    }

    public AuthenticatedLoginDTO getAuthenticatedLoginDTO() {
        return authenticatedLoginDTO;
    }
}
