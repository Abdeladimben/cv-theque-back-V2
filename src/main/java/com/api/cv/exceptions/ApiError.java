package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;

@Data
public class ApiError {

	private ErrorCode errorCode;
	private String message;
	private String date = ""+new Date();
	private String[] fieldsError;
	
	public ApiError(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.message = errorCode.getValue();
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

	@Override
	public String toString() {
		return "ApiError{" +
				"errorCode=" + errorCode +
				", message='" + message + '\'' +
				", date='" + date + '\'' +
				", fieldsError=" + Arrays.toString(fieldsError) +
				'}';
	}
}
