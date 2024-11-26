package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.services.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserKeycloakInfoResponse> getUserInfo() {
        UserKeycloakInfoResponse userInfoResponse = userService.getUserInfo();
        return ResponseEntity.ok(userInfoResponse);
    }
}
