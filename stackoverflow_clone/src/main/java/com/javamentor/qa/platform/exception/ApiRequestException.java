package com.javamentor.qa.platform.exception;

public class ApiRequestException extends RuntimeException {

    public ApiRequestException() {
    }

    public ApiRequestException(String message) {
        super(message);
    }
}
