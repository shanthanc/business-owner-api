package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static com.shanthan.businessowner.testutils.TestConstants.SOME_DOB_1;
import static java.time.format.DateTimeFormatter.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.*;

class BusinessOwnerWebServiceTest {

    @InjectMocks
    private BusinessOwnerWebService subject;

    @Mock
    private BusinessOwnerService mockBusinessOwnerService;

    @Mock
    private ValidationService mockValidationService;

    private BusinessOwner testBusinessOwner1;

    private BusinessOwner webTestBusinessOwner1;

    private BusinessOwner webTestBusinessOwner2;

    private BusinessOwner webTestBusinessOwner3;

    private List<BusinessOwner> webTestBusinessOwners;

    private BusinessOwner updatedWebTestBusinessOwner1;

    private BusinessOwner errorWebTestBusinessOwner;

    @BeforeEach
    void setUp() {
        openMocks(this);

        testBusinessOwner1 = BusinessOwner.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();

        webTestBusinessOwner1 = BusinessOwner.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dob(SOME_DOB_1.toString())
                .build();

        webTestBusinessOwner2 = BusinessOwner.builder()
                .businessId(2L)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .address(ADDRESS_OBJECT_2)
                .ssn(SOME_SSN_2)
                .phoneNumber(SOME_PHONE_2)
                .dob(SOME_DOB_2.toString())
                .build();

        webTestBusinessOwner3 = BusinessOwner.builder()
                .businessId(3L)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .address(ADDRESS_OBJECT_3)
                .ssn(SOME_SSN_3)
                .phoneNumber(SOME_PHONE_3)
                .dob(SOME_DOB_3.toString())
                .build();



        errorWebTestBusinessOwner = BusinessOwner.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dob("2017-01-01")
                .build();

        updatedWebTestBusinessOwner1 = BusinessOwner.builder()
                .businessId(1L)
                .firstName(UPDATED_FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dob(SOME_DOB_1.toString())
                .build();

        webTestBusinessOwners = new ArrayList<>();
        webTestBusinessOwners.add(webTestBusinessOwner1);
        webTestBusinessOwners.add(webTestBusinessOwner2);
        webTestBusinessOwners.add(webTestBusinessOwner3);
    }

    @Test
    void givenBusinessOwner_whenCalledToCreateANewBusinessOwnerInDb_thenCreateNewOwner() throws BusinessOwnerException {
        LocalDate dateOfBirth = LocalDate.parse(webTestBusinessOwner1.getDob(), ISO_LOCAL_DATE);
        when(mockValidationService.validDateOfBirth(anyString())).thenReturn(dateOfBirth);
        when(mockBusinessOwnerService.addBusinessOwner(webTestBusinessOwner1)).thenReturn(testBusinessOwner1);

        BusinessOwner result = subject.create(webTestBusinessOwner1);

        assertEquals(LocalDate.parse(webTestBusinessOwner1.getDob(), ISO_LOCAL_DATE), result.getDateOfBirth());
    }
    @Test
    void givenBadBusinessOwner_whenCalledToCreateANewBusinessOwnerInDbErrorOccurs_thenThrowBusinessOwnerException()
            throws BusinessOwnerException {
        when(mockValidationService.validDateOfBirth(anyString()))
                .thenThrow(new BusinessOwnerException(BAD_REQUEST, "someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.create(errorWebTestBusinessOwner));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }
    @Test
    void givenBusinessOwner_whenCalledToCreateANewBusinessOwnerInDbErrorOccurs_thenThrowBusinessOwnerException()
            throws BusinessOwnerException {
        when(mockValidationService.validDateOfBirth(anyString())).thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.create(errorWebTestBusinessOwner));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenBusinessOwnerToUpdate_whenCalledForUpdate_thenReturnUpdatedBusinessOwner() throws BusinessOwnerException {
        LocalDate dateOfBirth = LocalDate.parse(webTestBusinessOwner1.getDob(), ISO_LOCAL_DATE);
        when(mockValidationService.validDateOfBirth(anyString())).thenReturn(dateOfBirth);
        when(mockBusinessOwnerService.updateBusinessOwner(webTestBusinessOwner1))
                .thenReturn(updatedWebTestBusinessOwner1);

        BusinessOwner updatedBusinessOwner = subject.update(webTestBusinessOwner1);

        assertNotNull(updatedBusinessOwner);
        assertEquals(UPDATED_FIRST_NAME_1, updatedBusinessOwner.getFirstName());
    }

    @Test
    void givenBadBusinessOwnerToUpate_whenCalledForUpdateErrorOccurs_thenThrowNewBusinessOwnerException()
            throws BusinessOwnerException {
        when(mockValidationService.validDateOfBirth(anyString()))
                .thenThrow(new BusinessOwnerException(BAD_REQUEST, "someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.update(errorWebTestBusinessOwner));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenBusinessOwnerToUpate_whenCalledForUpdateErrorOccurs_thenThrowNewBusinessOwnerException()
            throws BusinessOwnerException {
        when(mockValidationService.validDateOfBirth(anyString())).thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.update(errorWebTestBusinessOwner));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenBusinessId_whenRequestedToRetrieveBusinessOwner_thenRetrieveBusinessOwner()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(true);
        when(mockBusinessOwnerService.getBusinessOwnerByBusinessId(anyLong())).thenReturn(webTestBusinessOwner1);

        BusinessOwner result = subject.getBusinessOwnerById(1L);

        assertNotNull(result);
        assertEquals(webTestBusinessOwner1.getFirstName(), result.getFirstName());
    }

    @Test
    void givenBusinessId_whenRequestedToRetrieveBusinessOwnerErrorOccurs_thenThrowBusinessOwnerExceptionWith400Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(true);
        when(mockBusinessOwnerService.getBusinessOwnerByBusinessId(anyLong()))
                .thenThrow(new BusinessOwnerException(BAD_REQUEST, "someException"));
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerById(1L));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenBusinessId_whenRequestedToRetrieveBusinessOwnerErrorOccurs_thenThrowBusinessOwnerExceptionWith500Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(true);
        when(mockBusinessOwnerService.getBusinessOwnerByBusinessId(anyLong()))
                .thenThrow(new RuntimeException( "someException"));
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerById(1L));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenNonExistentBusinessId_whenRequestMadeForBusinessOwnerErrorOccurs_thenThrowExceptionWith404Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.doesBusinessOwnerWithIdExist(anyLong())).thenReturn(false);
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerById(1L));

        assertEquals(NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void givenValidBusinessId_whenRequestedToDelete_thenDeleteOwnerAndReturnTrue() throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong())).thenReturn(true);
        boolean result = subject.deleteBusinessOwner(1L);

        assertTrue(result);

    }
    @Test
    void givenNonexistentBusinessId_whenRequestedToDelete_thenReturnFalse() throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong())).thenReturn(false);
        boolean result = subject.deleteBusinessOwner(1L);

        assertFalse(result);
    }

    @Test
    void givenBusinessId_whenRequestedToDeleteAppExceptionIsThrown_thenThrowNewExceptionWithSameHttpStatusAsThrown()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong()))
                .thenThrow(new BusinessOwnerException(BAD_REQUEST, "someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.deleteBusinessOwner(1L));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenBusinessId_whenRequestedToDeleteSomeExceptionIsThrown_thenThrowNewExceptionWith500StatusAsThrown()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.deleteBusinessOwnerById(anyLong()))
                .thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.deleteBusinessOwner(1L));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenExistingFirstName_whenRequestedForBusinessOwnersWithIt_thenReturnTheList()
            throws BusinessOwnerException {

        List<BusinessOwner> firstNameList = webTestBusinessOwners.stream()
                .filter(bo -> bo.getFirstName().equals(FIRST_NAME_1))
                .toList();
        when(mockBusinessOwnerService.getBusinessOwnerListByFirstName(anyString()))
                .thenReturn(firstNameList);
        List<BusinessOwner> result = subject.businessOwnerListByFirstName(FIRST_NAME_1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(FIRST_NAME_1, result.get(0).getFirstName());
        assertEquals(FIRST_NAME_1, result.get(1).getFirstName());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void givenEmptyFirstName_whenRequestedForBusinessOwnersWithIt_thenThrowAppExceptionWith400Status(String fName) {
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByFirstName(fName));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenFirstName_whenBusinessOwnerListWithItRequestedErrorOccurs_thenThrowNewAppExceptionWithStatus()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByFirstName(anyString()))
                .thenThrow(new BusinessOwnerException(NOT_FOUND, "someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByFirstName(FIRST_NAME_1));

        assertEquals(NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void givenFirstName_whenBusinessOwnerListWithItRequestedErrorOccurs_thenThrowNewExceptionWith500Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByFirstName(anyString()))
                .thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByFirstName(FIRST_NAME_1));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenExistingLastName_whenRequestedForBusinessOwnersWithIt_thenReturnTheList()
            throws BusinessOwnerException {

        List<BusinessOwner> lastNameList = webTestBusinessOwners.stream()
                .filter(bo -> bo.getLastName().equals(LAST_NAME_2))
                .toList();
        when(mockBusinessOwnerService.getBusinessOwnerListByLastName(anyString()))
                .thenReturn(lastNameList);
        List<BusinessOwner> result = subject.businessOwnerListByLastName(LAST_NAME_2);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(LAST_NAME_2, result.get(0).getLastName());
        assertEquals(LAST_NAME_2, result.get(1).getLastName());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void givenEmptyLastName_whenRequestedForBusinessOwnersWithIt_thenThrowAppExceptionWith400Status(String lName) {
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByLastName(lName));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenLastName_whenBusinessOwnerListWithItRequestedErrorOccurs_thenThrowNewAppExceptionWithStatus()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByLastName(anyString()))
                .thenThrow(new BusinessOwnerException(NOT_FOUND, "someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByLastName(LAST_NAME_1));

        assertEquals(NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void givenLastName_whenBusinessOwnerListWithItRequestedErrorOccurs_thenThrowNewExceptionWith500Status()
            throws BusinessOwnerException {
        when(mockBusinessOwnerService.getBusinessOwnerListByLastName(anyString()))
                .thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.businessOwnerListByLastName(LAST_NAME_1));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }
}