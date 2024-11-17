package com.api.cv.services.auth;

import org.springframework.stereotype.Service;

import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.RegisterRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements ISignupService {
    
    private final IKeycloakService keycloakService;

	@Override
	public void createUser(RegisterRequestDto registerRequestDto) {

		keycloakService.Signup(registerRequestDto);
	}

    
}
