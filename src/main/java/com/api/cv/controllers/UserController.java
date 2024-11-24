package com.api.cv.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.services.UserInfoProvider;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
	
	private final UserInfoProvider userInfoProvider;

    @GetMapping("/info")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserKeycloakInfoResponse> getUserInfo() {
      
        String username = userInfoProvider.getUsername();
        String email = userInfoProvider.getEmail();
        String Date =userInfoProvider.getDate();
        List<String> roles = userInfoProvider.getRoles();
        List<String> groups = userInfoProvider.getGroups();

  
        UserKeycloakInfoResponse userInfoResponse = UserKeycloakInfoResponse.builder()
           
                .username(username)
                .email(email)
                .date(Date)
                .Roles(roles)
                .groups(groups)
                .build();

        return ResponseEntity.ok(userInfoResponse);
    }
    
    
}
