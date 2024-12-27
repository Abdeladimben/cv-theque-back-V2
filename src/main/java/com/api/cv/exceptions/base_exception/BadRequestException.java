package com.api.cv.exceptions.base_exception;

import com.api.cv.enums.ErrorCode;

public class BadRequestException extends ApiErrorException {

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}

}
