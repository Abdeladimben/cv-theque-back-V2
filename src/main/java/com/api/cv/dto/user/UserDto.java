package com.api.cv.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDto {
	
	@Schema(description = "The username of the user", example = "john_doe")
	private String userName;
	
	@Schema(description = "The email address of the user", example = "john.doe@example.com")
	private String email;
	

}
