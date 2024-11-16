package com.api.cv.services.auth;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;

public interface IAuthService {
	
	LoginResponseDto login(LoginRequestDto loginRequestDto);

}
