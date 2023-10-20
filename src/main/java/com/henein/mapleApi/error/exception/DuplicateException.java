package com.henein.mapleApi.error.exception;


import com.henein.mapleApi.error.ErrorCode;

public class DuplicateException extends BusinessException {

    public DuplicateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
