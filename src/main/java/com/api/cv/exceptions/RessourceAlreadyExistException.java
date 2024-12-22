package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class RessourceAlreadyExistException extends BadRequestException {

	public RessourceAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}

}
