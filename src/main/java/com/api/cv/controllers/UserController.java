package com.api.cv.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.services.UserInfoProvider;
import com.api.cv.services.auth.IAuthService;
import com.api.cv.services.user.IUserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {
	
	/*

    private final IUserService userService;
   

    // Endpoint to get user info based on authorization token
    @GetMapping("userinfo")
    public ResponseEntity<UserKeycloakInfoResponse> getUserInfoFromToken(@RequestHeader("authorization") String bearerToken) {
        return ResponseEntity.ok(userService.userInfo(bearerToken));
    }
*/
	
	  // SecurityContextHolder
	
	/*
	 private final UserInfoProvider userInfoProvider;
  
    @GetMapping("/info")
    @SecurityRequirement(name = "bearerAuth")

    public String getUserInfo() {
        String userId = userInfoProvider.getUserId();
        String username = userInfoProvider.getUsername();
        String email = userInfoProvider.getEmail();
        List<String> roles = userInfoProvider.getRoles();
        List<String> RealmRoles=userInfoProvider.getRealmRoles();
        return "User ID: " + userId + ", Username: " + username + ", Email: " + email + ", Roles: " + roles +",Realm Roles"+RealmRoles;
    }
    
    */
	
	
	private final UserInfoProvider userInfoProvider;

    @GetMapping("/info")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UserKeycloakInfoResponse> getUserInfo() {
        String userId = userInfoProvider.getUserId();
        String username = userInfoProvider.getUsername();
        String email = userInfoProvider.getEmail();
        String Date =userInfoProvider.getDate();
        List<String> roles = userInfoProvider.getRoles();
        List<String> groups = userInfoProvider.getGroups();

  
        UserKeycloakInfoResponse userInfoResponse = UserKeycloakInfoResponse.builder()
                .userId(userId)
                .username(username)
                .email(email)
                .date(Date)
                .Roles(roles)
                .groups(groups)
                .build();

        return ResponseEntity.ok(userInfoResponse);
    }
    
    
}
