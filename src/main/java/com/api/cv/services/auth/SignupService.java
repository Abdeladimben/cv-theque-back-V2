package com.api.cv.services.auth;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.api.cv.config.security.KeycloakProperties;
import com.api.cv.dto.auth.RegisterRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignupService implements ISignupService {
    
    private final RestTemplate restTemplate;
    private final KeycloakProperties keycloakProperties;
    
    
    @Override
    public String getAdminAccessToken() {
        String url = keycloakProperties.getAuthServerUrl() + "/realms/" + keycloakProperties.getRealm() + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", keycloakProperties.getClientId());
        map.add("username", keycloakProperties.getAdminUser());
        map.add("password", keycloakProperties.getAdminPassword());
        map.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try {
            Map<String, Object> response = restTemplate.postForObject(url, request, Map.class);
            return response != null ? (String) response.get("access_token") : null;
        } catch (HttpClientErrorException e) {
            System.err.println("Error retrieving token: " + e.getResponseBodyAsString());
            throw e;
        }
        
    }
    
    

    
    
    private String getUserIdByUsername(String username, String token) {
        String url = "http://localhost:8080/admin/realms/cv/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);

        if (response.getStatusCode().is2xxSuccessful() && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }
        return null;
    }
    
    private boolean assignRoleToUser(String userId, String roleName, String token) {
        String roleId = getRoleIdByName(roleName, token);
        if (roleId == null) {
            return false;
        }

        String url = "http://localhost:8080/admin/realms/cv/users/" + userId + "/role-mappings/clients/"+ keycloakProperties.getClientUuid();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Role payload
        Map<String, Object> role = new HashMap<>();
        role.put("id", roleId);
        role.put("name", roleName);

        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(Collections.singletonList(role), headers);
        try {
            restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return true;
        } catch (HttpClientErrorException e) {
            System.err.println("Failed to assign role: " + e.getResponseBodyAsString());
            return false;
        }
    }
    
    private String getRoleIdByName(String roleName, String token) {
        String url = "http://localhost:8080/admin/realms/cv/clients/" +keycloakProperties.getClientUuid()+ "/roles/" + roleName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("id");
        }
        return null;
    }





	@Override
	public String createUser(RegisterRequestDto registerRequestDto) {
		String token = getAdminAccessToken();
        if (token == null) {
            return "Failed to retrieve admin access token";
        }

        String url = "http://localhost:8080/admin/realms/cv/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Using LinkedHashMap to ensure order, though regular Map will also work
        Map<String, Object> user = new HashMap<>();
        user.put("username", registerRequestDto.getUsername());
        user.put("email", registerRequestDto.getEmail());
        user.put("enabled", true);

        // Credentials should be a list of maps
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", registerRequestDto.getPassword());
        credentials.put("temporary", false);  // As boolean, not string

        // Add credentials as a list
        user.put("credentials", List.of(credentials));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);
        try {
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Step 2: Get User ID and Assign Role
            String userId = getUserIdByUsername( registerRequestDto.getUsername(), token);
            if (userId != null) {
                boolean roleAssigned = assignRoleToUser(userId,  registerRequestDto.getRoleName(), token);
                return roleAssigned ? "User created and role assigned successfully" 
                                    : "User created, but role assignment failed";
            } else {
                return "User created, but failed to retrieve user ID for role assignment";
            }
        } else {
            return "Error creating user: " + response.getBody();
        }
    } catch (HttpClientErrorException e) {
        return "Error creating user: " + e.getResponseBodyAsString();
    }
	}




/*
	@Override
	public String createUser(String username, String email, String password, String roleName) {

	        String token = getAdminAccessToken();
	        if (token == null) {
	            return "Failed to retrieve admin access token";
	        }

	        String url = "http://localhost:8080/admin/realms/cv/users";

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setBearerAuth(token);

	        // Using LinkedHashMap to ensure order, though regular Map will also work
	        Map<String, Object> user = new HashMap<>();
	        user.put("username", username);
	        user.put("email", email);
	        user.put("enabled", true);

	        // Credentials should be a list of maps
	        Map<String, Object> credentials = new HashMap<>();
	        credentials.put("type", "password");
	        credentials.put("value", password);
	        credentials.put("temporary", false);  // As boolean, not string

	        // Add credentials as a list
	        user.put("credentials", List.of(credentials));

	        HttpEntity<Map<String, Object>> request = new HttpEntity<>(user, headers);
	        try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            // Step 2: Get User ID and Assign Role
	            String userId = getUserIdByUsername(username, token);
	            if (userId != null) {
	                boolean roleAssigned = assignRoleToUser(userId, roleName, token);
	                return roleAssigned ? "User created and role assigned successfully" 
	                                    : "User created, but role assignment failed";
	            } else {
	                return "User created, but failed to retrieve user ID for role assignment";
	            }
	        } else {
	            return "Error creating user: " + response.getBody();
	        }
	    } catch (HttpClientErrorException e) {
	        return "Error creating user: " + e.getResponseBodyAsString();
	    }
	//        
	        
	        
	    
	}
    
    
    */
    
    
    
}
