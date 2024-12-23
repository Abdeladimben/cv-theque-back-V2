package com.api.cv.consuming.keycloak.services.signup;

import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.ApiErrorException;

import java.util.List;

public interface IKeycloakSignUpService {

    String Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException;

}
