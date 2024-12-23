package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

public class BadRequestException extends ApiErrorException{

	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}

}
