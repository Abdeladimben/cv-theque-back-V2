package com.api.cv.consuming.keycloak.services;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.api.cv.consuming.keycloak.model.KeycloakAssignRole;
import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.consuming.keycloak.model.KeycloakUserInformation;
import com.api.cv.exceptions.ConflitException;
import com.api.cv.exceptions.NotFoundException;
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
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponentsBuilder;

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
            manageKeycloakErrorWithException(e);
            return null;
        }
    }

    @Override
    public void Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException {
        LoginRequestDto loginRequestDto =
            LoginRequestDto.builder()
                .password(keycloakProperties.getAdminPassword())
                .username(keycloakProperties.getAdminUser())
            .build();
        String token = login(loginRequestDto).getAccess_token();

        HttpEntity<Map<String, Object>> request = setRequestForSignUp(registerRequestDto,token);

        try {
            restTemplate.exchange(keycloakProperties.getUriUsers(), HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            log.error("ERROR in SIGNUP : {}",e.getMessage());
            manageKeycloakErrorWithException(e);// HTTP client error during user creation
        }

        assignRoleToUser(
            getUserIdByUsername(registerRequestDto.getUsername(), token), registerRequestDto.getRoleName(), token
        );

    }

    @Override
    public String getUserIdByUsername(String username, String token) throws ApiErrorException {
        URI url = UriComponentsBuilder
                    .fromUriString(keycloakProperties.getUriUsers())
                    .queryParam("username", username)
                .build()
            .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List<KeycloakUserInformation>> response =
                restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<List<KeycloakUserInformation>>() {});

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && !response.getBody().isEmpty()) {
            KeycloakUserInformation keycloakUserInformation = response.getBody().get(0);
            if(keycloakUserInformation.getId()==null)
                throw new ApiErrorException(ErrorCode.AU004);
            return keycloakUserInformation.getId();
        }
        throw new ApiErrorException(ErrorCode.AU002); // User doesn't exist
    }

    @Override
    public void assignRoleToUser(String userId, String roleName, String token) throws ApiErrorException {
        String roleId = getRoleIdByName(roleName, token);

        String url = String.format(keycloakProperties.getUriUsersAssignRole(),userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        KeycloakAssignRole role = new KeycloakAssignRole(roleId,roleName);
        HttpEntity<KeycloakAssignRole> request = new HttpEntity<>(role, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        } catch (HttpClientErrorException e) {
            manageKeycloakErrorWithException(e);
        }
    }

    @Override
    public String getRoleIdByName(String roleName, String token) throws ApiErrorException {
        String url = keycloakProperties.getUriRoles() + "/" + roleName ;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<KeycloakRole> response = restTemplate.exchange(url, HttpMethod.GET, entity, KeycloakRole.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().getId();
        }
        throw new ApiErrorException(ErrorCode.AK003); // Role ID retrieval failed
    }


    private HttpEntity<Map<String, Object>> setRequestForSignUp(RegisterRequestDto registerRequestDto,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> user = new HashMap<>();
        user.put("username", registerRequestDto.getUsername());
        user.put("email", registerRequestDto.getEmail());
        user.put("enabled", true);

        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", registerRequestDto.getPassword());
        credentials.put("temporary", false);

        user.put("credentials", List.of(credentials));

        return new HttpEntity<>(user, headers);
    }

    public void manageKeycloakErrorWithException(HttpClientErrorException e) throws ApiErrorException {
        if (HttpStatus.CONFLICT.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            } else if (e.getResponseBodyAsString().contains("User exists with same email")){
                throw new ConflitException(ErrorCode.AK005);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.UNAUTHORIZED.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.NOT_FOUND.value() == e.getStatusCode().value()) {
            if (e.getResponseBodyAsString().contains("Role not found")) {
                throw new NotFoundException(ErrorCode.AK007);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else {
            throw new ApiErrorException(ErrorCode.AK001);
        }
    }

    public void manageKeycloakErrorWithResponseError(ResponseEntity<?> response) throws ApiErrorException {
        if (HttpStatus.CONFLICT.equals(response.getStatusCode())) {
            if(response.getBody()==null)
                throw new ApiErrorException(ErrorCode.AK001);
            String responseBody = response.getBody().toString();
            if (responseBody.contains("User exists with same username")) {
                throw new ConflitException(ErrorCode.AK004);
            } else if (responseBody.contains("User exists with same email")) {
                throw new ConflitException(ErrorCode.AK005);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())) {
            if(response.getBody()==null)
                throw new ApiErrorException(ErrorCode.AK001);
            String responseBody = response.getBody().toString();
            if (responseBody.contains("Invalid user credentials")) {
                throw new ApiErrorException(ErrorCode.AK006);
            } else {
                throw new ApiErrorException(ErrorCode.AK001);
            }
        } else if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApiErrorException(ErrorCode.AK001);
        }
    }

}
