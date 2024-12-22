package com.api.cv.consuming.keycloak.services;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.exceptions.RessourceAlreadyExistException;
import com.api.cv.exceptions.RessourceDbNotFoundException;

public interface IKeycloakService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiErrorException, RessourceDbNotFoundException;

	void Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException,RessourceAlreadyExistException;
	
     String getUserIdByUsername(String username, String token) throws ApiErrorException;
	
     boolean assignRoleToUser(String userId, String roleName, String token)throws ApiErrorException;
	
     String getRoleIdByName(String roleName, String token)throws ApiErrorException;
}
