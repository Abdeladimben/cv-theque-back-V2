package com.api.cv.consuming.keycloak.services.signup;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.consuming.keycloak.model.KeycloakAssignRole;
import com.api.cv.consuming.keycloak.model.KeycloakRole;
import com.api.cv.consuming.keycloak.model.KeycloakUserInformation;
import com.api.cv.consuming.keycloak.services.IKeycloakService;
import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.auth.RegisterRequestDto;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.helpers.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class KeycloakSignUpService implements IKeycloakSignUpService {

    private final KeycloakProperties keycloakProperties;

    private final IKeycloakService keycloakService;

    private final RestTemplate restTemplate;

    @Override
    public String Signup(RegisterRequestDto registerRequestDto) throws ApiErrorException {
        LoginRequestDto loginRequestDto =
            LoginRequestDto.builder()
                .password(keycloakProperties.getAdminPassword())
                .username(keycloakProperties.getAdminUser())
            .build();
        String token = keycloakService.login(loginRequestDto).getAccess_token();

        HttpEntity<Map<String, Object>> request = setRequestForSignUp(registerRequestDto,token);

        try {
            restTemplate.exchange(keycloakProperties.getUriUsers(), HttpMethod.POST, request, String.class);
        } catch (HttpClientErrorException e) {
            log.error("ERROR in SIGNUP : {}",e.getMessage());
            Utils.manageKeycloakErrorWithException(e);// HTTP client error during user creation
        }
        String userId = getUserIdByUsername(registerRequestDto.getUsername(), token);
        if(registerRequestDto.getRoles()!=null && !registerRequestDto.getRoles().isEmpty()){
            assignRoleToUser(userId, registerRequestDto.getRoles(), token);
        }
        return userId;
    }

    private String getUserIdByUsername(String username, String token) throws ApiErrorException {
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

    private void assignRoleToUser(String userId, List<String> roles, String token) throws ApiErrorException {
        List<KeycloakAssignRole> listRole = new ArrayList<>();
        for (String roleName : roles) {
            String roleId = getRoleIdByName(roleName, token);
            listRole.add(new KeycloakAssignRole(roleId,roleName));
        }
        String url = String.format(keycloakProperties.getUriUsersAssignRole(),userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<List<KeycloakAssignRole>> request = new HttpEntity<>(listRole, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        } catch (HttpClientErrorException e) {
            Utils.manageKeycloakErrorWithException(e);
        }

    }

    private String getRoleIdByName(String roleName, String token) throws ApiErrorException {
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
}
