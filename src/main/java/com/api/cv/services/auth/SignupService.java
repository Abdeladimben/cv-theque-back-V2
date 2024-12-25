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
        saveUserWithRoles(keycloakUserId, registerRequestDto,listRoles);
    }

    private List<Role> getRoles(List<String> roles) throws RessourceDbNotFoundException {
        List<Role> listRoles = new ArrayList<>();
        for (String label : roles) {
            Role role = roleRepository.findByLabel(label)
                    .orElseThrow(()-> new RessourceDbNotFoundException(ErrorCode.AR001));
            listRoles.add(role);
        }
        return listRoles;
    }

    private void saveUserWithRoles(String keycloakUserId , RegisterRequestDto registerRequestDto , List<Role> listRoles){
        User user = new User();
        user.setUserName(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setKeycloakId(keycloakUserId);
        userRepository.save(user);
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : listRoles) {
            userRoles.add(new UserRole(user,role));
        }
        userRoleRepository.saveAll(userRoles);
    }

}
