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
@Schema(description = "Request payload for Login")

public class LoginRequestDto {
	
	
    @Schema(description = "Username of the user", example = "johndoe", required = true)
	private String username;
	
    @Schema(description = "Password of the user", example = "password123", required = true)
	private String password;

}
