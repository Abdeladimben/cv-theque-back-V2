package com.api.cv.consuming.keycloak.services;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.dto.auth.RegisterRequestDto;

public interface IKeycloakService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto);

	void Signup(RegisterRequestDto registerRequestDto);
	
     String getUserIdByUsername(String username, String token) ;
	
     boolean assignRoleToUser(String userId, String roleName, String token);
	
     String getRoleIdByName(String roleName, String token);
}
