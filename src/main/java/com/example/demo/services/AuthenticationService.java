package com.example.demo.services;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import com.example.demo.models.dtos.UserLoginDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    public void handleInvalidPassword(String rawPassword, String encodedPassword) {
        //Password encoder checks if the password is valid, if it's not - throws exception
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        //uses security context holder & security context logout handler to logout
    }

    public AuthenticatedLoginDTO login(UserLoginDTO loginDTO) {
        //checks the user credentials and maps to dto upon success with the needed from the client parameters
        return new AuthenticatedLoginDTO();
    }
}
