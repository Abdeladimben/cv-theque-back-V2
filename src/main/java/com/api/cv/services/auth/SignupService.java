package com.api.cv.services.auth;

import com.api.cv.consuming.keycloak.services.signup.IKeycloakSignUpService;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.entities.Role;
import com.api.cv.entities.User;
import com.api.cv.entities.UserRole;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.RessourceAlreadyExistException;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.repositories.RoleRepository;
import com.api.cv.repositories.UserRepository;
import com.api.cv.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.cv.consuming.keycloak.services.signup.IKeycloakSignUpService;
import com.api.cv.entities.Role;
import com.api.cv.entities.UserRole;
import com.api.cv.helpers.Utils;
import com.api.cv.repositories.RoleRepository;
import com.api.cv.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.entities.User;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.exceptions.RessourceAlreadyExistException;
import com.api.cv.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements ISignupService {
    
    private final IKeycloakSignUpService keycloakSignUpService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

	@Override
	public void createUser(RegisterRequestDto registerRequestDto) throws ApiErrorException {
        Optional<User> userOptional = userRepository.findByUserName(registerRequestDto.getUsername());
        if (userOptional.isPresent()) {
            throw new RessourceAlreadyExistException(ErrorCode.AU001);
        }
        List<Role> listRoles = getRoles(registerRequestDto.getRoles());
        String keycloakUserId = keycloakSignUpService.Signup(registerRequestDto);
        User user = saveUserWithRoles(keycloakUserId, registerRequestDto);
        if(Utils.isNotNullAndNotEmpty(registerRequestDto.getRoles())) {
            saveUserRoles(user, registerRequestDto.getRoles());
        }
    }

    private User saveUserWithRoles(String keycloakUserId , RegisterRequestDto registerRequestDto){
        User user = new User();
        user.setUserName(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setKeycloakId(keycloakUserId);
        return userRepository.save(user);
    }

    private void saveUserRoles(User user,List<String> roles) {
        List<UserRole> userRoles = new ArrayList<>();
        for (String label : roles) {
            Role role = roleRepository.findByLabel(label);
            userRoles.add(new UserRole(user,role));
        }
        userRoleRepository.saveAll(userRoles);
    }

}
