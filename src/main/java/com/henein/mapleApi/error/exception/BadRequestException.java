package com.henein.mapleApi.error.exception;


import com.henein.mapleApi.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
