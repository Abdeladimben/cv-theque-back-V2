package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class TechnicalException extends ApiErrorException{

    public TechnicalException(ErrorCode errorCode) {
        super(errorCode);
    }
}
