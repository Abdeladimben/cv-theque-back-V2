package com.api.cv.exceptions;

import java.util.Date;

import com.api.cv.enums.ErrorCode;

public class ApiError {

	private ErrorCode errorCode;
	private String message;
	private String date = ""+new Date();
	private String[] fieldsError;
	
	public ApiError(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.toString();
	}
	
	public ApiError(ErrorCode errorCode,String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public ApiError(ErrorCode errorCode,String message,String[] fieldsError) {
		this.errorCode = errorCode;
		this.message = message;
		this.fieldsError = fieldsError;
	}

}
