package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.auth.loginRequestDto;
import com.api.cv.dto.auth.loginResponseDto;
import com.api.cv.services.auth.IAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final IAuthService authService;
	
	@PostMapping("login")
	public ResponseEntity<loginResponseDto> login(@RequestBody loginRequestDto loginRequestDto){
		return ResponseEntity.ok(authService.login(loginRequestDto));
		 
	}
	
	@PostMapping("sign-up")
	public void signUp(){
		
		 
	}

}
