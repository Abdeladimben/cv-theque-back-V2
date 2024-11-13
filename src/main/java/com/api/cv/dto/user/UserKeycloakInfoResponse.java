package com.api.cv.dto.user;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@Builder
public class UserKeycloakInfoResponse {

	private final String userId;
    private final String username;
    private final String email;
    private final String date;

    private final List<String> Roles;
    private final List<String> groups;
  
}
