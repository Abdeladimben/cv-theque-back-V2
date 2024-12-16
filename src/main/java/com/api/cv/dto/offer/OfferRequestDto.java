package com.api.cv.dto.offer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "OfferRequestDto", description = "Request payload for creating a new job offer")
public class OfferRequestDto {

    @Schema(description = "Title of the job offer", example = "Software Engineer", required = true)
    private String title;

    @Schema(description = "Detailed description of the job", example = "Looking for a skilled Java developer to work on enterprise applications.", required = true)
    private String description;

    @Schema(description = "Position or post for the job", example = "Java Developer", required = true)
    private String post;

    @Schema(description = "City where the job is located", example = "Casablanca", required = true)
    private String ville;

    @Schema(description = "Salary offered for the position", example = "4000", required = true)
    private double remuneration;

    @Schema(description = "Duration of the contract in months", example = "12", required = true)
    private int dureeContrat;

    @Schema(description = "Code of the offer status (e.g., 1 for Pending, 2 for Active)", example = "1", required = true)
    private String statusCode;

    @Schema(description = "Code of the contract type (e.g., 1 for Full-Time, 2 for Part-Time)", example = "1", required = true)
    private String contractTypeCode;
}