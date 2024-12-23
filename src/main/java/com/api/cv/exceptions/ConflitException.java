package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

public class ConflitException extends ApiErrorException{

	public ConflitException(ErrorCode errorCode) {
		super(errorCode);
	}

}
