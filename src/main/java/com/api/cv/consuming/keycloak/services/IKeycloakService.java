package com.api.cv.consuming.keycloak.services;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.ApiErrorException;

public interface IKeycloakService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiErrorException;

	void Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException;

     String getUserIdByUsername(String username, String token) throws ApiErrorException;

    void assignRoleToUser(String userId, String roleName, String token)throws ApiErrorException;

     String getRoleIdByName(String roleName, String token)throws ApiErrorException;
}
