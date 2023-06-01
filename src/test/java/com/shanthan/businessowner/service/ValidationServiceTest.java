package com.shanthan.businessowner.service;

import com.shanthan.businessowner.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

class ValidationServiceTest {

    @InjectMocks
    private ValidationService subject;
    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Alabama", "AL", "Alaska", "AK", "Arizona", "AZ", "Arkansas", "AR", "California", "CA", "Colorado", "CO", "Connecticut", "CT", "Delaware", "DE", "Florida", "FL", "Georgia", "GA", "Hawaii", "HI", "Idaho", "ID", "Illinois", "IL", "Indiana", "IN", "Iowa", "IA", "Kansas", "KS", "Kentucky", "KY", "Louisiana", "LA", "Maine", "ME", "Maryland", "MD", "Massachusetts", "MA", "Michigan", "MI", "Minnesota", "MN", "Mississippi", "MS", "Missouri", "MO", "Montana", "MT", "Nebraska", "NE", "Nevada", "NV", "New Hampshire", "NH", "New Jersey", "NJ", "New Mexico", "NM", "New York", "NY", "North Carolina", "NC", "North Dakota", "ND", "Ohio", "OH", "Oklahoma", "OK", "Oregon", "OR", "Pennsylvania", "PA", "Rhode Island", "RI", "South Carolina", "SC", "South Dakota", "SD", "Tennessee", "TN", "Texas", "TX", "Utah", "UT", "Vermont", "VT", "Virginia", "VA", "Washington", "WA", "West Virginia", "WV", "Wisconsin", "WI", "Wyoming", "WY"})
    void givenAValidStateString_whenValidateStateCalled_thenReturnTrue(String state) {
        boolean result = subject.validateState(state);
        assertTrue(result);
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "", "whatever", "2142134", "q2423asfs", "ADFFESAADF"})
    void givenAInvalidValidStateString_whenValidateStateCalled_thenReturnFalse(String state) {
        boolean result = subject.validateState(state);
        assertFalse(result);
    }
}