package com.api.cv.services.auth;

import com.api.cv.dto.auth.loginRequestDto;
import com.api.cv.dto.auth.loginResponseDto;

public interface IAuthService {
	
	loginResponseDto login(loginRequestDto loginRequestDto);

}
