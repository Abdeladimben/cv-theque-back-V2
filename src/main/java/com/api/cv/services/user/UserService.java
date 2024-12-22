package com.api.cv.services.user;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.api.cv.dto.user.UserKeycloakInfoResponse;
import com.api.cv.entities.User;
import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.ApiErrorException;
import com.api.cv.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;

    public String getUserId(Jwt jwt) {
        return jwt.getClaimAsString("sub");
    }

    public String getUsername(Jwt jwt) {
    	if(jwt != null) {
            return jwt.getClaimAsString("preferred_username");
    	} else {
    		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    		if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
    			jwt = (Jwt) authentication.getPrincipal();
    			return jwt.getClaimAsString("preferred_username");
    		} 
    		return null;
    	}
    }

    public String getEmail(Jwt jwt) {
        return jwt.getClaimAsString("email");
    }

    public List<String> getRoles(Jwt jwt) {
        if (jwt == null) {
            return List.of();
        }

        // Extract roles for a specific client (e.g., "cv-bff")
        Map<String, Object> resourceAccess = jwt.getClaimAsMap("resource_access");
        return resourceAccess != null && resourceAccess.containsKey("cv-bff")
            ? (List<String>) ((Map<String, Object>) resourceAccess.get("cv-bff")).get("roles")
            : List.of();
    }

    public List<String> getGroups(Jwt jwt) {
        return jwt.getClaimAsStringList("groups");
    }

    public String getDate(Jwt jwt) {
        return jwt.getClaimAsString("Date");
    }

    public UserKeycloakInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            String username = getUsername(jwt);
            String email = getEmail(jwt);
            String date = getDate(jwt);
            List<String> roles = getRoles(jwt);
            List<String> groups = getGroups(jwt);

            return UserKeycloakInfoResponse.builder()
                    .username(username)
                    .email(email)
                    .date(date)
                    .Roles(roles)
                    .groups(groups)
                    .build();
        }
        return null;
    }
    
    public User getUserConnected() throws ApiErrorException {
    	return userRepository.findByUserName(getUsername(null))
    			.orElseThrow(() -> new ApiErrorException(ErrorCode.A500));
    }
}
