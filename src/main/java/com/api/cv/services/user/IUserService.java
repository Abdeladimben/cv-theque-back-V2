package com.api.cv.services.user;

import com.api.cv.dto.user.UserKeycloakInfoResponse;

public interface IUserService {
	
	UserKeycloakInfoResponse userInfo(String token);

}
