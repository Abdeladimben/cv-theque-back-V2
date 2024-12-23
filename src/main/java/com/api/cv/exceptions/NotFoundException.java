package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class NotFoundException extends ApiErrorException{

	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
