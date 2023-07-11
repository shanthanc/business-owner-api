package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}