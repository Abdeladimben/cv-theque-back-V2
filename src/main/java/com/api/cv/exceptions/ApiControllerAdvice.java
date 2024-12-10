package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;
import com.api.cv.exceptions.logger.ExceptionHandlerLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiControllerAdvice {

	private final ExceptionHandlerLogger exceptionHandlerLoggerImpl;
	
	@ExceptionHandler({ApiErrorException.class})
	public ResponseEntity<ApiError> handle(ApiErrorException ex) {
		exceptionHandlerLoggerImpl.handle(ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(ex.getErrorCode()));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ApiError> handle(MethodArgumentNotValidException e) {

		exceptionHandlerLoggerImpl.handle(e);

		StringBuilder str = new StringBuilder();

		str.append("Arguments non valides :");

		for (final FieldError error : e.getBindingResult().getFieldErrors()) {
			str.append(" [" + error.getField() + ": " + error.getDefaultMessage() + "]");
		}
		for (final ObjectError error : e.getBindingResult().getGlobalErrors()) {
			str.append(" [" + error.getObjectName() + ": " + error.getDefaultMessage() + "]");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(ErrorCode.A500, str.toString()));
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ApiError> handle(Exception e) {
		exceptionHandlerLoggerImpl.handle(e);
		ErrorCode code = ErrorCode.A500;
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiError(code, code.getValue()));
	}

}
