package com.example.demo.services.wantedToAchieve.events;

import com.example.demo.models.dtos.UserLoginDTO;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileEditSuccessEvent extends ApplicationEvent {
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final UserLoginDTO loginDTO;

    public ProfileEditSuccessEvent(Object source, HttpServletRequest request, HttpServletResponse response, UserLoginDTO loginDTO) {
        super(source);
        this.request = request;
        this.response = response;
        this.loginDTO = loginDTO;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public UserLoginDTO getLoginDTO() {
        return loginDTO;
    }
}
