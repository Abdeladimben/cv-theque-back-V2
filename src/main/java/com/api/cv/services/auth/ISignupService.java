package com.api.cv.services.auth;

import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.exceptions.RessourceAlreadyExistException;

public interface ISignupService {
	
	public void createUser(RegisterRequestDto registerRequestDto) throws ApiErrorException,RessourceAlreadyExistException ;

}
