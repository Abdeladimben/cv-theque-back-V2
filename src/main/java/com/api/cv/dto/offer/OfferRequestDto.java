package com.api.cv.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="OfferRequestDto",description = "Request payload for Offer")
public class OfferRequestDto {
	
	@Schema(description = "title of the offer", example = "Software Engineer", required = true)
	private String title;
	
	@Schema(description = "Description of the offer", example = "Looking for a skilled Java developer", required = true)	
    private String description;
}
