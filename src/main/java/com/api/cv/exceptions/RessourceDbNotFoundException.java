package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.BadRequestException;

public class RessourceDbNotFoundException extends BadRequestException {

	public RessourceDbNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}