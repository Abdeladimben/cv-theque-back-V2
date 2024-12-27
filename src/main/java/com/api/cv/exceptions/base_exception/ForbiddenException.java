package com.api.cv.exceptions.base_exception;

import com.api.cv.enums.ErrorCode;

public class ForbiddenException extends ApiErrorException {

	public ForbiddenException(ErrorCode errorCode) {
		super(errorCode);
	}

}
