package com.api.cv.exceptions;

import java.io.Serial;

import com.api.cv.enums.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiErrorException extends Exception{
	
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;
	
	private String message;
	
	public ApiErrorException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
