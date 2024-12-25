package com.api.cv.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;




@Data

public class ResponseWrapper<T> {

	private T response;
	private List<T> responseList;
	private String successMessage;
	private Integer errorCode; 
	
	
	
	  public ResponseWrapper(T response, String successMessage, Integer errorCode) {
	        this.response = response;
	        this.successMessage = successMessage;
	        this.errorCode = errorCode;
	    }
	  
	  
	  
	  public ResponseWrapper(List<T> responseList,String successMessage, Integer errorCode) {
		  this.responseList=responseList;
		  this.successMessage = successMessage;
	        this.errorCode = errorCode;
	    }
		  
		  
		  
	  }
	  
	 
	 

