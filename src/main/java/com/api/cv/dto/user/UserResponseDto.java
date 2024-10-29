package com.api.cv.dto.user;

import com.api.cv.dto.auth.loginResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

	private String userName;
	
	private String email;
}
