package com.shanthan.businessowner.service;

import com.shanthan.businessowner.repository.BusinessOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

class BusinessOwnerServiceTest {

    @InjectMocks
    private BusinessOwnerService subject;

    @Mock
    private BusinessOwnerRepository mockBusinessOwnerRepository;

    private
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    @DisplayName("test to check if getBusinessOwner from Database is working as expected")
    void test_ToCheckFor_getBusinessOwnerByBusinessIdFromDb() {
        when(mockBusinessOwnerRepository.getBusinessOwnerEntityByBusinessId(anyLong()));
    }
}