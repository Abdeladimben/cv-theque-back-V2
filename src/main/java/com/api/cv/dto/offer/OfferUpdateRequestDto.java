package com.api.cv.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name="OfferUpdateRequestDto",description = "Request Update payload for Offer")
public class OfferUpdateRequestDto {
	
	@Schema(description = "uuid of the offer", example = "-fe-fez-rè-ezf-è", required = true)
	private String uuid;
	
	@Schema(description = "title of the offer", example = "Software Engineer", required = true)
	private String title;
	
	@Schema(description = "Description of the offer", example = "Looking for a skilled Java developer", required = true)	
    private String description;

	@Schema(description = "Position or post for the job", example = "Java Developer", required = true)
	private String post;

	@Schema(description = "City where the job is located", example = "Casablanca", required = true)
	private String ville;

	@Schema(description = "Salary offered for the position", example = "4000", required = true)
	private double remuneration;

	@Schema(description = "Duration of the contract in months", example = "12", required = true)
	private int dureeContrat;

	@Schema(description = "Status code of the offer", example = "1", required = true)
	private String statusCode;

	@Schema(description = "Contract type code", example = "1", required = true)
	private String contractTypeCode;

}
