package com.api.cv.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.dto.auth.RegisterRequestDto;

import com.api.cv.services.auth.IAuthService;
import com.api.cv.services.auth.ISignupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
@RestController
@RequestMapping("${endpoint.prefix}auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "APIs - LOGIN | SIGNUP")
public class AuthController {
	
	private final IAuthService authService;
	private final ISignupService signupService;
	
  
	@Operation(
        summary = "Login endpoint",
        description = "Accepts username and password, and returns an access token."
     
    )
    @ApiResponse(
        responseCode = "200",
        description = "Login successful",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = LoginResponseDto.class))
    )
    @ApiResponse(responseCode = "400", description = "Invalid username or password")
    @ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping("login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
		return ResponseEntity.ok(authService.login(loginRequestDto));
	}
	

	@Operation(summary="Sign up endpoint",description="create a user with the infos provided")
	@ApiResponse(responseCode = "200",description="user created successfully")
	@ApiResponse(responseCode = "400", description = "Invalid infos ")
	@ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody RegisterRequestDto registerRequestDto) {
        signupService.createUser(registerRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
