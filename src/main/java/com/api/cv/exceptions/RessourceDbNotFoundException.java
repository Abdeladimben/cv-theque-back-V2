package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class RessourceDbNotFoundException extends BadRequestException {

	public RessourceDbNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}