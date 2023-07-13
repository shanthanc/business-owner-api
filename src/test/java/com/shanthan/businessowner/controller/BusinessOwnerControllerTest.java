package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import com.shanthan.businessowner.service.BusinessOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static com.shanthan.businessowner.testutils.TestConstants.SOME_DOB_3;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.springframework.http.HttpStatus.*;

class BusinessOwnerControllerTest {

    @InjectMocks
    private BusinessOwnerController subject;

    @Mock
    private BusinessOwnerService mockBusinessOwnerService;

    private BusinessOwner testBusinessOwner1;
    private BusinessOwner testBusinessOwner2;
    private BusinessOwner testBusinessOwner3;

    private BusinessOwner noIdBusinessOwner1;

    private BusinessOwner updatedBusinessOwner;

    private List<BusinessOwner> testBusinessOwners;

    @BeforeEach
    void setUp() {
        openMocks(this);

        noIdBusinessOwner1 = BusinessOwner.builder()
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();

        testBusinessOwner1 = BusinessOwner.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();

        testBusinessOwner2 = BusinessOwner.builder()
                .businessId(2L)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .address(ADDRESS_OBJECT_2)
                .ssn(SOME_SSN_2)
                .phoneNumber(SOME_PHONE_2)
                .dateOfBirth(SOME_DOB_2)
                .build();

        testBusinessOwner3 = BusinessOwner.builder()
                .businessId(3L)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .address(ADDRESS_OBJECT_3)
                .ssn(SOME_SSN_3)
                .phoneNumber(SOME_PHONE_3)
                .dateOfBirth(SOME_DOB_3)
                .build();

        updatedBusinessOwner = BusinessOwner.builder()
                .businessId(1L)
                .firstName(UPDATED_FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();

        testBusinessOwners = new ArrayList<>();

        testBusinessOwners.add(testBusinessOwner1);
        testBusinessOwners.add(testBusinessOwner2);
        testBusinessOwners.add(testBusinessOwner3);



    }

    @Test
    void givenRequestToAddBusinessOwner_whenRequestMadeAndCreateSuccessful_thenReturnResponseWith201Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.addBusinessOwner(any(BusinessOwner.class))).thenReturn(testBusinessOwner1);

        ResponseEntity response = subject.addBusinessOwner(testBusinessOwner1);

        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());

    }

    @Test
    void givenBusinessOwnerToUpdate_whenRequestMadeAndUpdateSuccessful_thenReturnResponseWith200Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.updateBusinessOwner(any(BusinessOwner.class))).thenReturn(updatedBusinessOwner);

        ResponseEntity response = subject.updateBusinessOwner(testBusinessOwner1);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void givenNonExistentBusinessOwnerToUpdate_whenRequestMade_thenReturnResponseWith204Status() throws BusinessOwnerException {
        when(mockBusinessOwnerService.updateBusinessOwner(any(BusinessOwner.class)))
                .thenReturn(BusinessOwner.builder().build());

        ResponseEntity response = subject.updateBusinessOwner(testBusinessOwner1);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenExistentBusinessId_whenRequestMadeToRetieveBusinessOwnerWithSuchId_thenReturnResponseWith200Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(true);

        ResponseEntity response = subject.getBusinessOwnerWithId(1L);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void givenNonExistentBusinessId_whenRequestMadeToRetieveBusinessOwnerWithSuchId_thenReturnResponseWith204Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(false);

        ResponseEntity response = subject.getBusinessOwnerWithId(1L);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenExistentFirstName_whenRequestMadeToGetListWithSameFirstName_thenReturnResponseWith200Status()
            throws BusinessOwnerException {
        List<BusinessOwner> firstNameBusinessOwners = testBusinessOwners.stream()
                .filter(bo -> bo.getFirstName().equals(FIRST_NAME_1))
                .sorted(Comparator.comparing(BusinessOwner::getFirstName))
                .toList();

        when(mockBusinessOwnerService.getBusinessOwnerListByFirstName(anyString())).thenReturn(firstNameBusinessOwners);

        ResponseEntity response = subject.getBusinessOwnersWithFirstName(FIRST_NAME_1);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void givenNonExistentFirstName_whenRequestMadeToGetListWithSameFirstName_thenReturnResponseWith204Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByFirstName(anyString())).thenReturn(emptyList());

        ResponseEntity response = subject.getBusinessOwnersWithFirstName(FIRST_NAME_1);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenExistentLastName_whenRequestMadeToGetListWithSameLastName_thenReturnResponseWith200Status()
            throws BusinessOwnerException {
        List<BusinessOwner> lastNameBusinessOwners = testBusinessOwners.stream()
                .filter(bo -> bo.getLastName().equals(LAST_NAME_2))
                .sorted(Comparator.comparing(BusinessOwner::getLastName))
                .toList();

        when(mockBusinessOwnerService.getBusinessOwnerListByLastName(anyString())).thenReturn(lastNameBusinessOwners);

        ResponseEntity response = subject.getBusinessOwnersWithLastName(LAST_NAME_2);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void givenNonExistentLastName_whenRequestMadeToGetListWithSameLastName_thenReturnResponseWith204Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByLastName(anyString())).thenReturn(emptyList());

        ResponseEntity response = subject.getBusinessOwnersWithLastName(LAST_NAME_2);

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenBusinessOwnerToDelete_whenRequestMadeAndDeleteSuccessful_thenReturnResponseWithStatus200Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong())).thenReturn(true);

        ResponseEntity response = subject.deleteBusinessOwner(1L);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
    }

    @Test
    void givenNonExistentBusinessOwnerToDelete_whenRequestMade_thenReturnResponseWithStatus203Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong())).thenReturn(false);

        ResponseEntity response = subject.deleteBusinessOwner(1L);

        assertNotNull(response);
        assertEquals(ACCEPTED, response.getStatusCode());
    }
}