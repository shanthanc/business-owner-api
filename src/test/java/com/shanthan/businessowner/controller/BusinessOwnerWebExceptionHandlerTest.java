package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import freemarker.core.Environment;
import freemarker.core.InvalidReferenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

class BusinessOwnerWebExceptionHandlerTest {

    @InjectMocks
    private BusinessOwnerWebExceptionHandler subject;

    @Mock
    private Model model;

    private BusinessOwnerException businessOwnerException;

    private RuntimeException globalException;

    private MissingServletRequestParameterException parameterException;

    private InvalidReferenceException invalidReferenceException;

    @BeforeEach
    void setUp() {
        openMocks(this);

        businessOwnerException  = new BusinessOwnerException(APP_EXCEPTION_MSG);
        globalException = new RuntimeException(GLOBAL_EXCEPTION_MSG);
        parameterException = new MissingServletRequestParameterException(MISSING_SERVLET_EXCEPTION_PARAMETER,
                MISSING_SERVLET_EXCEPTION_PARAMETER_TYPE);
        invalidReferenceException = new InvalidReferenceException(INVALID_REFERENCE_EXCEPTION_MSG,
                Environment.getCurrentEnvironment());
    }

    @Test
    void givenApplicationException_whenItOccurs_thenRenderErrorPage() {
        String result = subject.applicationException(model, businessOwnerException);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE, APP_EXCEPTION_MSG);

        assertEquals(ERROR_PAGE, result);
    }

    @Test
    void givenGlobalException_whenItOccurs_thenRenderErrorPage() {
        String result = subject.globalException(model, globalException);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE, GLOBAL_EXCEPTION_MSG);

        assertEquals(ERROR_PAGE, result);
    }

    @Test
    void givenMissingServletRequestParameterException_whenItOccurs_thenRenderErrorPage() {
        String result = subject.missingServletRequestParameterException(model, parameterException);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE, MISSING_SERVLET_ERROR_ATTRIBUTE);

        assertEquals(ERROR_PAGE, result);

    }

    @Test
    void givenInvalidReferenceException_whenItOccurs_thenRenderErrorPage() {
        String result = subject.invalidReferenceException(model, invalidReferenceException);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_REFERENCE_EXCEPTION_MSG);

        assertEquals(ERROR_PAGE, result);
    }
}