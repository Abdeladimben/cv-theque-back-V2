package com.api.cv.services.auth;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.entities.User;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.exceptions.RessourceAlreadyExistException;
import com.api.cv.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements ISignupService {
    
    private final IKeycloakService keycloakService;
    private final UserRepository userRepository;
    private final KeycloakProperties keycloakProperties;
	@Override
	public void createUser(RegisterRequestDto registerRequestDto) throws ApiErrorException,RessourceAlreadyExistException {
		
	Optional<User> userOptional = userRepository.findByUserName(registerRequestDto.getUsername());
    if (userOptional.isPresent()) {
        throw new RessourceAlreadyExistException(ErrorCode.AU003);
    }
    
    keycloakService.Signup(registerRequestDto);
    User user = new User();
    user.setUserName(registerRequestDto.getUsername());
    user.setEmail(registerRequestDto.getEmail());
    userRepository.save(user);
}

}
