package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import freemarker.core.InvalidReferenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = BusinessOwnerWebController.class)
@Slf4j
public class BusinessOwnerWebExceptionHandler {
    @ExceptionHandler(BusinessOwnerException.class)
    public String applicationException(Model model, BusinessOwnerException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String globalException(Model model, Exception exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String missingServletRequestParameterException(Model model,
                                                          MissingServletRequestParameterException exception) {
        model.addAttribute("errorMessage", exception.getMessage());

        return "error";
    }

    @ExceptionHandler(InvalidReferenceException.class)
    public String invalidReferenceException(Model model, InvalidReferenceException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
