package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.dto.auth.loginResponseDto;
import com.api.cv.services.auth.IAuthService;
import com.api.cv.services.auth.ISignupService;
import com.api.cv.services.auth.SignupService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final IAuthService authService;
	private final ISignupService signupService;
	
	@PostMapping("login")
	public ResponseEntity<loginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
		return ResponseEntity.ok(authService.login(loginRequestDto));
		 
	}
	
	 @PostMapping("/signup")
	    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto) {
	        String result = signupService.createUser(registerRequestDto);
	        return ResponseEntity.ok(result);
	    }
}
