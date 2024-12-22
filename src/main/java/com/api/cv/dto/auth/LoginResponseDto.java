	package com.api.cv.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Schema(description = "Response payload for Login")
public class LoginResponseDto {
	
    @Schema(description = "Access token for the user", example = "eyJhbGciOiJIUzI1...")
    private String access_token;
    
    @Schema(description = "Refresh token for the user", example = "eyJhbGciOiJIUzI1...")
    private String refresh_token;
    
    @Schema(description = "token type", example = "Bearer ,...")
    private String token_type;
    @Schema(description = "token expiration ", example = "300s,...")

    private Integer expires_in;
    private String scope;
}
