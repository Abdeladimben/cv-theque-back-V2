package com.api.cv.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class ApplyDto {

	@Schema(description = "uuid of the offer", example = "eerrff55g4g5g5g...", required = true)
	private String offerUuid;
	

	
}
