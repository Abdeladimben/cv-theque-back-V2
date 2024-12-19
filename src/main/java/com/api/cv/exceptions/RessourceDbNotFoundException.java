package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class RessourceDbNotFoundException extends ApiErrorException {

	public RessourceDbNotFoundException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}

}
