package com.api.cv.consuming.keycloak.services.signup;

import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.base_exception.ApiErrorException;

public interface IKeycloakSignUpService {

    String Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException;

}
