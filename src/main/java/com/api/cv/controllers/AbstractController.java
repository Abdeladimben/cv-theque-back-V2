package com.api.cv.controllers;

import org.springframework.http.ResponseEntity;

import com.api.cv.dto.ResponseWrapper;

public abstract class AbstractController {
	
	
	public <T> ResponseEntity<ResponseWrapper<T>> buildListResponse(ResponseWrapper responseWrapper ){
		
     

		return ResponseEntity.ok(responseWrapper);
			
		
	}
	
	public <T> ResponseEntity<ResponseWrapper<T>> buildResponse(ResponseWrapper responseWrapper ){
		
	     

		return ResponseEntity.ok(responseWrapper);
			
		
	}
	

	
	
}
