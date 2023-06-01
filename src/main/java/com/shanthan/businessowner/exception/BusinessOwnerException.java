package com.shanthan.businessowner.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BusinessOwnerException extends Exception {

    private String message;

    private HttpStatus httpStatus;

    public BusinessOwnerException() {
        super();
    }

    public BusinessOwnerException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessOwnerException(HttpStatus httpStatus, String message) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public BusinessOwnerException(String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
    }

    public BusinessOwnerException(HttpStatus httpStatus, String message, Throwable throwable) {
        super(message, throwable);
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
