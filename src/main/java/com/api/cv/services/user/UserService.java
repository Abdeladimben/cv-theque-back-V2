package com.api.cv.services.user;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.dto.user.UserKeycloakInfoResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final KeycloakProperties keycloakProperties;
    private final RestTemplate restTemplate;
	
	@Override
	public UserKeycloakInfoResponse userInfo(String bearerToken) {
		// TODO Auto-generated method stub
        String userInfoUri = keycloakProperties.getUserInfoUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", bearerToken);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<UserKeycloakInfoResponse> response = restTemplate.exchange(
            userInfoUri,
            HttpMethod.GET,
            entity,
            UserKeycloakInfoResponse.class
        );

        UserKeycloakInfoResponse userInfo = response.getBody();
		return userInfo;
	}
	
	

}
