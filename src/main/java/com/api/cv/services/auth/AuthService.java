package com.api.cv.services.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.entities.User;
import com.api.cv.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    
    private final IKeycloakService keycloakService;
    
    
    private final UserRepository userRepository;

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		
	    Optional<User> userOptional = userRepository.findByUserName(loginRequestDto.getUsername());

		
	    if (userOptional.isPresent()) {
	      
	        return keycloakService.login(loginRequestDto);
	    } else {
	        
	        return new LoginResponseDto("User not found", null, null, null, null);
	    }
	}

}
