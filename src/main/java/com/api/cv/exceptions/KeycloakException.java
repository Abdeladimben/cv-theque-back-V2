package com.api.cv.exceptions;

import com.api.cv.enums.ErrorCode;

public class KeycloakException extends ApiErrorException{

    public KeycloakException(ErrorCode errorCode) {
        super(errorCode);
    }
}
