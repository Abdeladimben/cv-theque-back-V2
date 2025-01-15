package com.api.cv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DocumentDto", description = "Response payload for upload a document")

public class DocumentDto {
	
	@Schema(description = "The Name of the file",example = "cv")
	private String nomDocument;
	
	@Schema(description = "The Extension of the file",example = "pdf")
	private String Extension;
	
	@Schema(description = "The size of the file",example = "90")
	private Long taile;
	
    @Schema(description = "The binary data of the file",example="SGVsbG8sIHRoaXMgaXMgYSB0ZXN0IGZpbGUh")
	private byte[ ] data;
	
	
	
	
	
	

}
