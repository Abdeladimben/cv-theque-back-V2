package com.api.cv.services.auth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.dto.auth.loginRequestDto;
import com.api.cv.dto.auth.loginResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService{
	
    private final KeycloakProperties keycloakProperties;
    private final RestTemplate restTemplate;

	@Override
	public loginResponseDto login(loginRequestDto loginRequestDto) {
		// TODO Auto-generated method stub
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", keycloakProperties.getClientId());
//        map.add("client_secret", keycloakProperties.getClientSecret());
        map.add("username", loginRequestDto.getUserName());
        map.add("password", loginRequestDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return restTemplate.postForObject(
            keycloakProperties.getTokenUri(),
            request,
            loginResponseDto.class
        );
	}

}
