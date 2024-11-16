package com.api.cv.services.auth;

import org.springframework.stereotype.Service;
import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    
    private final IKeycloakService keycloakService;

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		// TODO Auto-generated method stub
        return keycloakService.login(loginRequestDto);
	}

}
