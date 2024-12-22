package com.api.cv.consuming.keycloak.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@Service
@RequiredArgsConstructor
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
                keycloakProperties.getTokenUri(),
                request,
                LoginResponseDto.class
            );
        } catch (HttpClientErrorException e) {
            throw new ApiErrorException(ErrorCode.AK003); // Failed to log in
        }
    }

    @Override
    public void Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException {
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
            .password(keycloakProperties.getAdminPassword())
            .username(keycloakProperties.getAdminUser())
            .build();
        String token = login(loginRequestDto).getAccess_token();

        String url = "http://localhost:8080/admin/realms/cv/users";

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

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String userId = getUserIdByUsername(registerRequestDto.getUsername(), token);
                if (userId != null) {
                    boolean roleAssigned = assignRoleToUser(userId, registerRequestDto.getRoleName(), token);
                    if (!roleAssigned) {
                        throw new ApiErrorException(ErrorCode.AU003); // Role assignment failed
                    }
                } else {
                    throw new ApiErrorException(ErrorCode.AU004); // Failed to retrieve user ID
                }
            } else {
                throw new ApiErrorException(ErrorCode.AK002); // User creation failed
            }
        } catch (HttpClientErrorException e) {
            throw new ApiErrorException(ErrorCode.AK003); // HTTP client error during user creation
        }
    }

    @Override
    public String getUserIdByUsername(String username, String token) throws ApiErrorException {
        String url = "http://localhost:8080/admin/realms/cv/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        if (response.getStatusCode().is2xxSuccessful() && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }
        throw new ApiErrorException(ErrorCode.AU002); // User doesn't exist
    }

    @Override
    public boolean assignRoleToUser(String userId, String roleName, String token) throws ApiErrorException {
        String roleId = getRoleIdByName(roleName, token);
        if (roleId == null) {
            throw new ApiErrorException(ErrorCode.AU003); // Role assignment failed
        }

        String url = "http://localhost:8080/admin/realms/cv/users/" + userId + "/role-mappings/clients/"
            + keycloakProperties.getClientUuid();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> role = new HashMap<>();
        role.put("id", roleId);
        role.put("name", roleName);

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(Collections.singletonList(role), headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            throw new ApiErrorException(ErrorCode.AK003); // Failed to assign role
        }
    }

    @Override
    public String getRoleIdByName(String roleName, String token) throws ApiErrorException {
        String url = "http://localhost:8080/admin/realms/cv/clients/" + keycloakProperties.getClientUuid() + "/roles/" + roleName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("id");
        }
        throw new ApiErrorException(ErrorCode.AK003); // Role ID retrieval failed
    }
}
