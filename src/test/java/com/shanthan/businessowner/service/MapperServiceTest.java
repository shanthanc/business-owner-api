package com.shanthan.businessowner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.Address;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

class MapperServiceTest {
    @InjectMocks
    private MapperService subject;
    @Mock
    private ObjectMapper mockObjectMapper;

    @Mock
    private EncryptionService mockEncryptionService;
    private BusinessOwnerEntity testBusinessOwnerEntity;

    private BusinessOwner testBusinessOwner;


    @BeforeEach
    void setUp() {
        openMocks(this);

        testBusinessOwnerEntity = BusinessOwnerEntity.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_1_STRING)
                .ssn(ENCYRPTED_SSN_1)
                .phoneNumber(ENCRYPTED_PHONE_NUMBER_1)
                .dateOfBirth(ENCRYPTED_DOB_1)
                .build();

        testBusinessOwner = BusinessOwner.builder()
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();
    }

    @Test
    void givenBusinessOwnerEntity_whenCalledToMapToBusinessOwnerObject_thenReturnBusinessOwner() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Address expectedAddress = objectMapper.readValue(ADDRESS_1_STRING, Address.class);
        when(mockObjectMapper.readValue(anyString(), eq(Address.class))).thenReturn(expectedAddress);
        when(mockEncryptionService.decrypt(ENCYRPTED_SSN_1)).thenReturn(SOME_SSN_1);
        when(mockEncryptionService.decrypt(ENCRYPTED_PHONE_NUMBER_1)).thenReturn(SOME_PHONE_1);
        when(mockEncryptionService.decrypt(ENCRYPTED_DOB_1)).thenReturn(SOME_DOB_STRING_1);
        BusinessOwner result = subject.mapEntityToObject(testBusinessOwnerEntity);
        assertEquals(FIRST_NAME_1, result.getFirstName());
        assertEquals(LAST_NAME_1, result.getLastName());
        assertEquals(ADDRESS_CITY_1, result.getAddress().getCity());
        assertEquals(ADDRESS_ZIPCODE_1, result.getAddress().getZipcode());
        assertEquals(SOME_SSN_1, result.getSsn());
        assertEquals(SOME_PHONE_1, result.getPhoneNumber());
        assertEquals(SOME_DOB_1, result.getDateOfBirth());
    }

    @Test
    void givenBusinessOwnerEntity_whenCalledToMapToBusinessOwnerObjectErrorOccurs_thenThrowBusinessOwnerException()
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Address expectedAddress = objectMapper.readValue(ADDRESS_1_STRING, Address.class);
        when(mockObjectMapper.readValue(anyString(), eq(Address.class))).thenReturn(expectedAddress);
        when(mockEncryptionService.decrypt(ENCYRPTED_SSN_1)).thenReturn(SOME_SSN_1);
        when(mockEncryptionService.decrypt(ENCRYPTED_PHONE_NUMBER_1)).thenReturn(SOME_PHONE_1);
        when(mockEncryptionService.decrypt(ENCRYPTED_DOB_1)).thenThrow(new RuntimeException("someException"));

        BusinessOwnerException exception =
                assertThrows(BusinessOwnerException.class, () -> subject.mapEntityToObject(testBusinessOwnerEntity));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenBusinessOwner_whenCalledToMapToBusinessOwnerEntity_thenReturnBusinessOwnerEntity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String addressString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ADDRESS_OBJECT_1);
        when(mockObjectMapper.writeValueAsString(any(Address.class))).thenReturn(addressString);
        when(mockEncryptionService.encrypt(SOME_SSN_1)).thenReturn(ENCYRPTED_SSN_1);
        when(mockEncryptionService.encrypt(SOME_PHONE_1)).thenReturn(ENCRYPTED_PHONE_NUMBER_1);
        when(mockEncryptionService.encrypt(SOME_DOB_STRING_1)).thenReturn(ENCRYPTED_DOB_1);
        BusinessOwnerEntity result = subject.mapObjectToEntity(testBusinessOwner);

        assertEquals(FIRST_NAME_1, result.getFirstName());
        assertEquals(LAST_NAME_1, result.getLastName());
        assertEquals(ADDRESS_1_STRING, result.getAddress());
        assertEquals(ENCYRPTED_SSN_1, result.getSsn());
        assertEquals(ENCRYPTED_PHONE_NUMBER_1, result.getPhoneNumber());
        assertEquals(ENCRYPTED_DOB_1, result.getDateOfBirth());
    }

    @Test
    void givenBusinessOwner_whenCalledToMapToBusinessOwnerEntityErrorOccurs_thenThrowBusinessOwnerException()
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String addressString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ADDRESS_OBJECT_1);
        when(mockObjectMapper.writeValueAsString(any(Address.class))).thenReturn(addressString);
        when(mockEncryptionService.encrypt(SOME_SSN_1)).thenReturn(ENCYRPTED_SSN_1);
        when(mockEncryptionService.encrypt(SOME_PHONE_1)).thenThrow(new RuntimeException("someException"));
        when(mockEncryptionService.encrypt(SOME_DOB_STRING_1)).thenReturn(ENCRYPTED_DOB_1);

        BusinessOwnerException exception =
                assertThrows(BusinessOwnerException.class, () -> subject.mapObjectToEntity(testBusinessOwner));

        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenValidAddressString_whenMapAddressStringToObjectCalled_thenReturnValidAddressObject()
            throws JsonProcessingException, BusinessOwnerException {
        ObjectMapper objectMapper = new ObjectMapper();
        Address expectedAddress = objectMapper.readValue(ADDRESS_1_STRING, Address.class);
        when(mockObjectMapper.readValue(anyString(), eq(Address.class))).thenReturn(expectedAddress);
        Address address = subject.mapAddressStringToObject(ADDRESS_1_STRING);
        assertEquals(ADDRESS_CITY_1, address.getCity());
        assertEquals(ADDRESS_ZIPCODE_1, address.getZipcode());
    }

    @Test
    void givenAddressString_whenMapAddressStringToObjectCalledErrorOccurs_thenThrowsBusinessOwnerException() throws JsonProcessingException {
        when(mockObjectMapper.readValue(anyString(), eq(Address.class)))
                .thenThrow(new RuntimeException("someRuntimeException"));

        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.mapAddressStringToObject(ADDRESS_1_STRING));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenAddressObject_whenCalledToMapToString_thenReturnAddressString() throws JsonProcessingException,
            BusinessOwnerException {
        when(mockObjectMapper.writeValueAsString(any(Address.class)))
                .thenReturn(ADDRESS_1_STRING);
        String address = subject.mapAddressObjectToString(ADDRESS_OBJECT_1);
        assertEquals(ADDRESS_1_STRING, address);
    }

    @Test
    void givenAddressObject_whenMapAddressObjectToStringThrowsSomeRuntimeException_thenThrowsBusinessOwnerException()
            throws JsonProcessingException {
        when(mockObjectMapper.writeValueAsString(any(Address.class)))
                .thenThrow(new RuntimeException("someRuntimeException"));
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.mapAddressObjectToString(ADDRESS_OBJECT_1));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenValidSsn_whenSsnEncryptionCalled_thenReturnEncryptedSsn() throws Exception {
        when(mockEncryptionService.encrypt(anyString())).thenReturn(ENCYRPTED_SSN_1);
        String actualResult = subject.encryptField(SOME_SSN_1);
        assertEquals(ENCYRPTED_SSN_1, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       "})
    void givenNullOrEmptySsn_whenSsnEncryptiongCalled_thenThrowBusinessOwnerException(String ssn) throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptField(ssn));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenSomeSsn_whenSsnEncryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException() throws Exception {

        when(mockEncryptionService.encrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptField(SOME_SSN_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenValidPhoneNumber_whenSsnEncryptionCalled_thenReturnEncryptedPhoneNumber() throws Exception {
        when(mockEncryptionService.encrypt(anyString())).thenReturn(ENCRYPTED_PHONE_NUMBER_1);
        String actualResult = subject.encryptField(SOME_PHONE_1);
        assertEquals(ENCRYPTED_PHONE_NUMBER_1, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       "})
    void givenNullOrEmptyPhoneNumber_whenPhoneNumberEncryptionCalled_thenThrowBusinessOwnerException(String phone) throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptField(phone));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenSomePhoneNumber_whenPhoneNumberEncryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException() throws Exception {

        when(mockEncryptionService.encrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptField(SOME_PHONE_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenValidDob_whenDobEncryptionCalled_thenReturnEncryptedDobString() throws Exception {
        when(mockEncryptionService.encrypt(anyString())).thenReturn(ENCRYPTED_DOB_1);
        String actualResult = subject.encryptDob(SOME_DOB_1);
        assertEquals(ENCRYPTED_DOB_1, actualResult);
    }

    @Test
    void givenNullOrEmptyDob_whenDobEncryptionCalled_thenThrowBusinessOwnerException() throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptDob(null));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }


    @Test
    void givenSomeDob_whenDobEncryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException() throws Exception {
        when(mockEncryptionService.encrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.encryptDob(SOME_DOB_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }


    @Test
    void givenEncryptedPhoneNumber_whenPhoneNumberDecryptionCalled_thenReturnDecryptedPhoneNumber() throws Exception {
        when(mockEncryptionService.decrypt(anyString())).thenReturn(SOME_PHONE_1);
        String actualResult = subject.decryptField(ENCRYPTED_PHONE_NUMBER_1);
        assertEquals(SOME_PHONE_1, actualResult);
    }


    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       "})
    void givenNullOrEmptyPhone_whenPhoneDecryptionCalled_thenThrowBusinessOwnerException(String phone) throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptField(phone));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenSomePhoneNumber_whenPhoneNumDecryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException()
            throws Exception {

        when(mockEncryptionService.decrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptField(ENCYRPTED_SSN_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenEncryptedSsn_whenSsnDecryptionCalled_thenReturnDecryptedSsn() throws Exception {
        when(mockEncryptionService.decrypt(anyString())).thenReturn(SOME_SSN_1);
        String actualResult = subject.decryptField(ENCYRPTED_SSN_1);
        assertEquals(SOME_SSN_1, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       "})
    void givenNullOrEmptySsn_whenSsnDecryptionCalled_thenThrowBusinessOwnerException(String ssn) throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptField(ssn));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenSomeSsn_whenSsnDecryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException() throws Exception {

        when(mockEncryptionService.decrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptField(ENCYRPTED_SSN_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenEncryptedDob_whenDobDecryptionCalled_thenReturnDecryptedDob() throws Exception {
        when(mockEncryptionService.decrypt(anyString())).thenReturn(SOME_DOB_STRING_1);
        LocalDate actualResult = subject.decryptDob(ENCRYPTED_DOB_1);
        assertEquals(SOME_DOB_1, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "       "})
    void givenNullOrEmptyDob_whenDobDecryptionCalled_thenThrowBusinessOwnerException(String dob) throws Exception {
        BusinessOwnerException ex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptDob(dob));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenSomeDob_whenDobDecryptionCalledCausesRuntimeException_thenThrowBusinessOwnerException() throws Exception {
        when(mockEncryptionService.decrypt(anyString())).thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.decryptDob(ENCRYPTED_DOB_1));
        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

}