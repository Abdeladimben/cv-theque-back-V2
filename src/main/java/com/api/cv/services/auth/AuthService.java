package com.api.cv.services.auth;

import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.entities.User;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
    
    private final IKeycloakService keycloakService;
    
    
    private final UserRepository userRepository;

	@Override
	public LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiErrorException {
		
	    Optional<User> userOptional = userRepository.findByUserName(loginRequestDto.getUsername());

		
	    if (userOptional.isPresent()) {
	      
	        return keycloakService.login(loginRequestDto);
	    } else {
	        
	        throw new RessourceDbNotFoundException(ErrorCode.AU002);
	    }
	}

}
