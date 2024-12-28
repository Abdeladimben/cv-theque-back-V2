package com.api.cv.services.auth;

import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.base_exception.ApiErrorException;

public interface ISignupService {
	
	void createUser(RegisterRequestDto registerRequestDto) throws ApiErrorException;

}
