package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.auth.loginRequestDto;
import com.api.cv.dto.auth.loginResponseDto;
import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.services.auth.IAuthService;
import com.api.cv.services.user.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class UserController {

	private final IUserService userService;
	
	@GetMapping("userinfo")
	public ResponseEntity<UserKeycloakInfoResponse> login(@RequestHeader("authorization") String bearerToken){
		return ResponseEntity.ok(userService.userInfo(bearerToken));
		 
	}
}
