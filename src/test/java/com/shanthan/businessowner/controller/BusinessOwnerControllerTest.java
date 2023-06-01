package com.shanthan.businessowner.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.*;

class BusinessOwnerControllerTest {

    @InjectMocks
    private BusinessOwnerController subject;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName(" test to check for when the GET endpoint /getBusinessOwner/{businessId} is working as expected")
    void test_toCheckFor_getBusinessOwnerWithAValidBusinessId() {

    }
}