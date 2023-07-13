package com.shanthan.businessowner.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = BusinessOwnerController.class)
@Slf4j
public class BusinessOwnerExceptionHandler {
}
