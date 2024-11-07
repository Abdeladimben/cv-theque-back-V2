package com.api.cv.services.auth;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.loginResponseDto;

public interface IAuthService {
	
	loginResponseDto login(LoginRequestDto loginRequestDto);

}
