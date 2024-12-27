package com.api.cv.exceptions.base_exception;

import com.api.cv.enums.ErrorCode;

public class ConflitException extends ApiErrorException {

	public ConflitException(ErrorCode errorCode) {
		super(errorCode);
	}

}
