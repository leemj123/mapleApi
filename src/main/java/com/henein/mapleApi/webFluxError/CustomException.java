package com.henein.mapleApi.webFluxError;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
