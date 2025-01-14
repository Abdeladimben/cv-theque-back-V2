package com.api.cv.dto.offer;

import com.api.cv.dto.ReferentielDto;
import com.api.cv.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="OfferResponseDto",description = "Response payload for Offer")
public class OfferResponseDto {
	
	@Schema(description = "uuid of the offer", example = "eerrff55g4g5g5g...", required = true)
	private String uuid;
	
	@Schema(description = "title of the offer", example = "Software Engineer", required = true)
	private String title;
	
	@Schema(description = "Description of the offer", example = "Looking for a skilled Java developer", required = true)	
    private String description;
	
	@Schema(description = "City where the job is located", example = "Casablanca", required = true)
	private String ville;
	
	@Schema(description = " the user of the offer",  required = true)	
    private UserDto createdUser; 

	@Schema(description = " the status of the offer", example="Active", required = true)	
    private ReferentielDto offerStatus;
	
	@Schema(description = " the status of the offer", example="Active", required = true)	
    private ReferentielDto contractType;
	
}
