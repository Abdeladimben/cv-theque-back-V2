package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class UserNotConnectedException extends ApiErrorException {
	
	public UserNotConnectedException(ErrorCode errorCode) {
		super(errorCode);
		// TODO Auto-generated constructor stub
	}
}
