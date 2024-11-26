package com.api.cv.services.auth;

import com.api.cv.dto.auth.RegisterRequestDto;

public interface ISignupService {
	
	public void createUser(RegisterRequestDto registerRequestDto) ;

}
