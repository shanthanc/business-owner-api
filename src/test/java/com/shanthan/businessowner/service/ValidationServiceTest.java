package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;

import java.time.LocalDate;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class ValidationServiceTest {

    @InjectMocks
    private ValidationService subject;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Alabama", "AL", "Alaska", "AK", "Arizona", "AZ", "Arkansas", "AR", "California", "CA",
            "Colorado", "CO", "Connecticut", "CT", "Delaware", "DE", "Florida", "FL", "Georgia", "GA", "Hawaii", "HI",
            "Idaho", "ID", "Illinois", "IL", "Indiana", "IN", "Iowa", "IA", "Kansas", "KS", "Kentucky", "KY",
            "Louisiana", "LA", "Maine", "ME", "Maryland", "MD", "Massachusetts", "MA", "Michigan", "MI", "Minnesota",
            "MN", "Mississippi", "MS", "Missouri", "MO", "Montana", "MT", "Nebraska", "NE", "Nevada", "NV",
            "New Hampshire", "NH", "New Jersey", "NJ", "New Mexico", "NM", "New York", "NY", "North Carolina", "NC",
            "North Dakota", "ND", "Ohio", "OH", "Oklahoma", "OK", "Oregon", "OR", "Pennsylvania", "PA", "Rhode Island",
            "RI", "South Carolina", "SC", "South Dakota", "SD", "Tennessee", "TN", "Texas", "TX", "Utah", "UT",
            "Vermont", "VT", "Virginia", "VA", "Washington", "WA", "West Virginia", "WV", "Wisconsin", "WI", "Wyoming",
            "WY"})
    void givenAValidStateString_whenValidateStateCalled_thenReturnTrue(String state) {
        boolean result = subject.validateState(state);
        assertTrue(result);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {" ", "", "whatever", "2142134", "q2423asfs", "ADFFESAADF"})
    void givenAInvalidValidStateString_whenValidateStateCalled_thenReturnFalse(String state) {
        boolean result = subject.validateState(state);
        assertFalse(result);
    }

    @Test
    void givenValidDobString_whenCalledToValidate_returnValidLocalDate() throws BusinessOwnerException {
        LocalDate result = subject.validDateOfBirth(SOME_DOB_STRING_1);

        assertNotNull(result);
        assertEquals(SOME_DOB_1, result);
    }


    @Test
    void givenDobInFuture_whenCalledToValidate_throwExceptionWithStatus400() {
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.validDateOfBirth(SOME_DOB_AFTER_TODAY_STRING));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @Test
    void givenDobLessThan18YearsOfAge_whenCalledToValidate_throwExceptionWithStatus400() {
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.validDateOfBirth(SOME_DOB_BELOW_18_YEARS_STRING));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " "})
    void givenNullOrEmptyDob_whenCalledToValidate_throwExceptionWithStatus400(String dob) {
        BusinessOwnerException exception = assertThrows(BusinessOwnerException.class, () ->
                subject.validDateOfBirth(dob));

        assertEquals(BAD_REQUEST, exception.getHttpStatus());
    }
}