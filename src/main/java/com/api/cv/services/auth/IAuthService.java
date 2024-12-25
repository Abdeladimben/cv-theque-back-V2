package com.api.cv.services.auth;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.exceptions.base_exception.ApiErrorException;

public interface IAuthService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiErrorException;

}
