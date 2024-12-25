package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.BadRequestException;

public class RessourceAlreadyExistException extends BadRequestException {

	public RessourceAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}

}
