package com.api.cv.dto.user;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor

@Builder
@Schema(description = "Details about the authenticated user")

public class UserKeycloakInfoResponse {

    @Schema(description = "The username of the user", example = "john_doe")

    private final String username;
    
    @Schema(description = "The email address of the user", example = "john.doe@example.com")
    private final String email;
    
    @Schema(description = "birth Date of to the user", example = "31/01/2002")
    private final String date;
    
    @Schema(description = "Roles assigned to the user", example = "[\"USER\", \"ADMIN\"]")
    private final List<String> Roles;
    
    @Schema(description = "Groups assigned to the user", example = "[\"Archicadx\", \"SGMA\"]")
    private final List<String> groups;
  
}
