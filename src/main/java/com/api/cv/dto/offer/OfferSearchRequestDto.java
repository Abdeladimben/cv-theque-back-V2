package com.api.cv.dto.offer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data

public class OfferSearchRequestDto {
	
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

	@Schema(description = "Offre Status Code", example = "1", required = true)
	private String statusOffreCode;

	@Schema(description = "Type Contract Code", example = "1", required = true)
	private String typeContractCode;

	@Schema(description = "user who create the offer", example = "1", required = true)
	private String username;
	
	@Schema(description = "Min Date", example = "1", required = true)
	private LocalDate creationDateMin;
	
	@Schema(description = "Max Date", example = "1", required = true)
	private LocalDate creationDateMax;

    public LocalDateTime getCreationDateMinAsDateTime() {
        return creationDateMin != null ? creationDateMin.atStartOfDay() : null;
    }

    public LocalDateTime getCreationDateMaxAsDateTime() {
        return creationDateMax != null ? creationDateMax.atTime(23, 59, 59) : null;
    }
	
	
	
	

}
