package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.ErrorResponse;
import com.shanthan.businessowner.testutils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.io.InputStream;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class BusinessOwnerExceptionHandlerTest {

    @InjectMocks
    private BusinessOwnerExceptionHandler subject;

    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void givenHttpMessageNotReadableException_whenItOccurs_thenReturnErrorResponseWith400StatusAndMessage() {
        when(httpMessageNotReadableException.getMessage()).thenReturn(GLOBAL_EXCEPTION_MSG);
        ResponseEntity<ErrorResponse> response =
                subject.httpMessageNotReadableException(httpMessageNotReadableException);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void givenMethodArgumentNotValidException_whenItOccurs_thenReturnErrorResponseWith400StatusAndMessage() {
        FieldError fieldError = new FieldError("someObject", "someRejectedField",
                "someDefaultMessage");
        when(methodArgumentNotValidException.getFieldError()).thenReturn(fieldError);
        when(methodArgumentNotValidException.getMessage()).thenReturn(GLOBAL_EXCEPTION_MSG);


        ResponseEntity<ErrorResponse> response =
                subject.methodArgumentNotValidException(methodArgumentNotValidException);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());
        assertEquals(BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void givenBusinessOwnerException_whenItOccurs_thenReturnErrorResponseWithGivenHttpStatusAndMessage() {

        BusinessOwnerException businessOwnerException = new BusinessOwnerException(INTERNAL_SERVER_ERROR,
                APP_EXCEPTION_MSG);
        ResponseEntity<ErrorResponse> response = subject.businessOwnerException(businessOwnerException);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());

        assertEquals(businessOwnerException.getHttpStatus(), response.getStatusCode());
    }

    @Test
    void givenSomeException_whenItOccurs_thenReturnErrorResponseWith500StatusAndMessage() {

        ResponseEntity<ErrorResponse> response =
                subject.globalException(new RuntimeException(GLOBAL_EXCEPTION_MSG));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMessage());

        assertEquals(INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}