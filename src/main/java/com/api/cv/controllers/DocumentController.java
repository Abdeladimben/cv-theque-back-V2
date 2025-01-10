package com.api.cv.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.cv.dto.DocumentDto;
import com.api.cv.exceptions.RessourceDbNotFoundException;
import com.api.cv.exceptions.base_exception.ApiErrorException;
import com.api.cv.services.document.IDocumentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${endpoint.prefix}documents")
@Tag(name = "Document Controller", description = "APIs - GET ALL |GET BY UUID | UPLOAD | DOWNLOAD | DELETE")

public class DocumentController {
	
	
	private final IDocumentService iDocumentService;

	@Operation(summary = "Delete document", description = "Delete document by Uuid")
    @ApiResponse(responseCode = "201", description = "Document Deleted successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
	@DeleteMapping("{uuid}")
	public ResponseEntity<Void> delete(@PathVariable String uuid) throws RessourceDbNotFoundException {
		iDocumentService.delete(uuid);
		return ResponseEntity.noContent().build();
	
	}
	
	@Operation(summary = "upload a document", description = "upload a document with provided information")
    @ApiResponse(responseCode = "201", description = "Document uploaded successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

	public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file,@RequestParam String documentTypeCode) throws IOException, ApiErrorException{
		
		String ulpoad=iDocumentService.upload(file,documentTypeCode);
		
		return ResponseEntity.status(HttpStatus.OK).body(ulpoad);
		
	}
	
	@Operation(summary = "Download document", description = "Get a document by Uuid")
    @ApiResponse(responseCode = "201", description = "Document retrivied successfully")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
	@GetMapping("/download/{uuid}")
	    public ResponseEntity<byte[]> downloadFile(@PathVariable String uuid) {
	        
	        byte[] doc = iDocumentService.downloadFile(uuid);
	        String filename = "document.pdf"; 
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF); 
	        headers.setContentDispositionFormData("attachment", filename); //
	        return new ResponseEntity<>(doc, headers, HttpStatus.OK);
	    }

	    @Operation(summary = "get all documents of connected user", description = "Get all documents of connected user")
	    @ApiResponse(responseCode = "201", description = "Documents retrivied successfully")
	    @ApiResponse(responseCode = "400", description = "Bad request")
	    @ApiResponse(responseCode = "500", description = "Internal server error")
	    @GetMapping("/get/all")
	    public ResponseEntity<List<DocumentDto>> getDocsByUser() throws ApiErrorException  {

	        return ResponseEntity.ok(iDocumentService.getDocsByUser());
	    }
	    
	    
	    
   
	    
}
