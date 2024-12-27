package com.api.cv.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = " request payload for register")
public class RegisterRequestDto {

	@Schema(description = "The username of the user", example = "john_doe")
	private String username;

	@Schema(description = "The email address of the user", example = "john.doe@example.com")
	private String email;

	@Schema(description = "Password of the user", example = "password123", required = true)
	private String password;

	@Schema(description = "Role assigned to the user", example = "[\"USER\", \"ADMIN\"]")
	private List<String> roles;

}
