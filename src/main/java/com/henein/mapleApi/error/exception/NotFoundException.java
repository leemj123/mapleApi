package com.henein.mapleApi.error.exception;


import com.henein.mapleApi.error.ErrorCode;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
