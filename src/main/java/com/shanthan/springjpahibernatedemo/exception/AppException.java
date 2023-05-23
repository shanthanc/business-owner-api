package com.shanthan.springjpahibernatedemo.exception;

import org.springframework.http.HttpStatus;

public class AppException extends Exception {

    private String message;

    private HttpStatus httpStatus;

    public AppException() {
        super();
    }

    public AppException(String message) {
        super(message);
        this.message = message;
    }

    public AppException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public AppException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
