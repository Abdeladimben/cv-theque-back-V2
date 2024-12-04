package com.api.cv.dto;

import com.api.cv.dto.auth.LoginRequestDto;
import com.api.cv.dto.user.UserDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request and response payload for Offer")
public class OfferDto {
	
	
		@Schema(description = "title of the offer", example = "Software Engineer", required = true)	
		private String title;
		
		@Schema(description = "Description of the offer", example = "Looking for a skilled Java developer", required = true)	
	    private String description;
		
		
		@Schema(description = " the user of the offer",  required = true)	
	    private UserDto createdUser; 
	

		@Schema(description = " the status of the offer", example="Active", required = true)	
	    private String status;
}
