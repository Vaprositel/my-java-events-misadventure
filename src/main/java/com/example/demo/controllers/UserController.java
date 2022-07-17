package com.example.demo.controllers;

import com.example.demo.models.dtos.AuthenticatedLoginDTO;
import com.example.demo.models.dtos.UserProfileEditDTO;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/edit/{userId}")
    public ResponseEntity<AuthenticatedLoginDTO> editUserProfile(
            @PathVariable Long userId,
            @RequestParam("data") String userData,
            @RequestParam("profileImageFile") MultipartFile profileImageFile,
            @RequestParam("coverImageFile") MultipartFile coverImageFile,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonProcessingException {
        UserProfileEditDTO dto = new ObjectMapper().readValue(userData, UserProfileEditDTO.class);

        AuthenticatedLoginDTO editedProfile =
                this.userService.editUserProfile(userId, dto, profileImageFile, coverImageFile, request, response);

        return ResponseEntity.ok().body(editedProfile);
    }
}
