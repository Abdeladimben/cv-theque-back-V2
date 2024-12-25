package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.base_exception.ForbiddenException;

public class UserNotConnectedException extends ForbiddenException {
	
	public UserNotConnectedException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}
}
