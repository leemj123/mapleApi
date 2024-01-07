package com.henein.mapleApi.webFluxError;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private ErrorDetails error;

    // getters and setters

    @Getter
    public static class ErrorDetails {
        private String name;
        private String message;

        // getters and setters
    }
}
