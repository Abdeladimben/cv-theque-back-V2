package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class ForbiddenException extends Exception{

	/**
	 *
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	private String message;

	public ForbiddenException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

}
