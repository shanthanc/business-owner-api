package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.util.ObjectUtils.isEmpty;

@RestControllerAdvice(assignableTypes = BusinessOwnerController.class)
@Slf4j
public class BusinessOwnerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorResponse errorBody = ErrorResponse.builder()
                .httpStatus(BAD_REQUEST)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errorFieldMap = new HashMap<>();
        if (!isEmpty(exception.getFieldError())) {
            Object rejectedField = exception.getFieldError().getRejectedValue();
            errorFieldMap.put("field", exception.getFieldError().getField());

            if (!isEmpty(rejectedField)) {
                errorFieldMap.put("rejectedValue", rejectedField.toString());
            }
            errorFieldMap.put("defaultMessage", exception.getFieldError().getDefaultMessage());
        }

        ErrorResponse errorBody = ErrorResponse.builder()
                .httpStatus(BAD_REQUEST)
                .message(exception.getMessage())
                .errorFieldMap(errorFieldMap)
                .build();

        return ResponseEntity.badRequest().body(errorBody);
    }

    @ExceptionHandler(BusinessOwnerException.class)
    public ResponseEntity<ErrorResponse> businessOwnerException(BusinessOwnerException exception) {
        ErrorResponse errorBody = ErrorResponse.builder()
                .httpStatus(exception.getHttpStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getHttpStatus()).body(errorBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalException(Exception exception) {
        ErrorResponse errorBody = ErrorResponse.builder()
                .httpStatus(INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(errorBody);
    }

}
