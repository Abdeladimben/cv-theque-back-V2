package com.api.cv.services.auth;

import com.api.cv.dto.auth.RegisterRequestDto;

public interface ISignupService {
	
	public String createUser(RegisterRequestDto registerRequestDto) ;
	public String getAdminAccessToken();

}
