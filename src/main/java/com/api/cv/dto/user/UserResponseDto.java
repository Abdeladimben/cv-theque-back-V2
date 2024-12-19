package com.api.cv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

	@Schema(description = "The username of the user", example = "john_doe")
	private String userName;
	
	@Schema(description = "The email address of the user", example = "john.doe@example.com")
	private String email;
}
