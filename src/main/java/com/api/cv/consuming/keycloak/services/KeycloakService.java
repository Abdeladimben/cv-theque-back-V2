package com.api.cv.consuming.keycloak.services;

import java.util.List;

import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.helpers.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.LoginResponseDto;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ApiErrorException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Slf4j
@Service
public class KeycloakService implements IKeycloakService {

    private final KeycloakProperties keycloakProperties;
    private final RestTemplate restTemplate;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) throws ApiErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", keycloakProperties.getClientId());
        map.add("username", loginRequestDto.getUsername());
        map.add("password", loginRequestDto.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            return restTemplate.postForObject(
                keycloakProperties.getUriToken(),
                request,
                LoginResponseDto.class
            );
        } catch (HttpClientErrorException e) {
            log.error("ERROR in login : {}",e.getMessage());
            Utils.manageKeycloakErrorWithException(e);
            return null;
        }
    }

    @Override
    public List<KeycloakRole> getRoles() throws ApiErrorException {
        LoginRequestDto loginRequestDto =
                LoginRequestDto.builder()
                        .password(keycloakProperties.getAdminPassword())
                        .username(keycloakProperties.getAdminUser())
                        .build();
        String token = login(loginRequestDto).getAccess_token();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List<KeycloakRole>> response =
                restTemplate.exchange(keycloakProperties.getUriRoles(), HttpMethod.GET, entity,new ParameterizedTypeReference<List<KeycloakRole>>() {});

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody();
        }
        throw new ApiErrorException(ErrorCode.AK008); // Role ID retrieval failed
    }

}
