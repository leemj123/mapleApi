package com.henein.mapleApi.error.exception;

import com.henein.mapleApi.error.ErrorCode;

public class InternerServerException extends BusinessException {

    public InternerServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
