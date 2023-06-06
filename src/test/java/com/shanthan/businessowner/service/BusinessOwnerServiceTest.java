package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import com.shanthan.businessowner.repository.BusinessOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static com.shanthan.businessowner.testutils.TestConstants.ENCRYPTED_DOB_1;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.springframework.http.HttpStatus.*;

class BusinessOwnerServiceTest {

    @InjectMocks
    private BusinessOwnerService subject;

    @Mock
    private BusinessOwnerRepository mockBusinessOwnerRepository;

    @Mock
    private MapperService mockMapperService;

    private BusinessOwnerEntity testBusinessOwnerEntity1;
    private BusinessOwnerEntity testBusinessOwnerEntity2;
    private BusinessOwnerEntity testBusinessOwnerEntity3;

    private BusinessOwner testBusinessOwner1;
    private BusinessOwner testBusinessOwner2;
    private BusinessOwner testBusinessOwner3;

    private List<BusinessOwnerEntity> testBusinessOwnerEntities;

    private List<BusinessOwner> testBusinessOwners;

    @BeforeEach
    void setUp() {

        openMocks(this);

        testBusinessOwnerEntity1 = BusinessOwnerEntity.builder()
                .businessId(1L)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_1_STRING)
                .ssn(ENCYRPTED_SSN_1)
                .phoneNumber(ENCRYPTED_PHONE_NUMBER_1)
                .dateOfBirth(ENCRYPTED_DOB_1)
                .build();

        testBusinessOwnerEntity2 = BusinessOwnerEntity.builder()
                .businessId(2L)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .address(ADDRESS_2_STRING)
                .ssn(ENCYRPTED_SSN_2)
                .phoneNumber(ENCRYPTED_PHONE_NUMBER_2)
                .dateOfBirth(ENCRYPTED_DOB_2)
                .build();

        testBusinessOwnerEntity3 = BusinessOwnerEntity.builder()
                .businessId(3L)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .address(ADDRESS_3_STRING)
                .ssn(ENCYRPTED_SSN_3)
                .phoneNumber(ENCRYPTED_PHONE_NUMBER_3)
                .dateOfBirth(ENCRYPTED_DOB_3)
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

        testBusinessOwnerEntities = new ArrayList<>();
        testBusinessOwners = new ArrayList<>();

        testBusinessOwnerEntities.add(testBusinessOwnerEntity1);
        testBusinessOwnerEntities.add(testBusinessOwnerEntity2);
        testBusinessOwnerEntities.add(testBusinessOwnerEntity3);

        testBusinessOwners.add(testBusinessOwner1);
        testBusinessOwners.add(testBusinessOwner2);
        testBusinessOwners.add(testBusinessOwner3);
    }

    @Test
    void givenBusinessId_getBusinessOwnerByBusinessIdFromDbCalled_returnBusinesOwnerEntity()
            throws BusinessOwnerException {
        when(mockBusinessOwnerRepository.getBusinessOwnerEntityByBusinessId(anyLong()))
                .thenReturn(testBusinessOwnerEntity1);
        when(mockMapperService.mapEntityToObject(any(BusinessOwnerEntity.class)))
                .thenReturn(testBusinessOwner1);
        BusinessOwner result = subject.getBusinessOwnerByBusinessIdFromDb(1L);

        assertNotNull(result);
        assertEquals(1L, result.getBusinessId());
        assertEquals(testBusinessOwnerEntity1.getFirstName(), result.getFirstName());
    }

    @Test
    void givenBusinessId_getBusinessOwnerByBusinessIdFromDbCalledErrorOccurs_thenThrowBusinesOwnerException()
            throws BusinessOwnerException {
        when(mockBusinessOwnerRepository.getBusinessOwnerEntityByBusinessId(anyLong()))
                .thenThrow(new RuntimeException("someException"));
        when(mockMapperService.mapEntityToObject(any(BusinessOwnerEntity.class)))
                .thenReturn(testBusinessOwner1);


        BusinessOwnerException ex =
                assertThrows(BusinessOwnerException.class, () -> subject.getBusinessOwnerByBusinessIdFromDb(1L));

        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenFirstName_whenCalledToRetrieveListOfBusinessOwners_thenReturnBusinessOwnersWithTheFirstName() throws BusinessOwnerException {
        String firstName = FIRST_NAME_1;

        List<BusinessOwnerEntity> firstNameSameEntities = testBusinessOwnerEntities.stream()
                .filter(entity -> entity.getFirstName().equals(firstName))
                .sorted(Comparator.comparing(BusinessOwnerEntity::getFirstName))
                .toList();

        List<BusinessOwner> firstNameBusinessOwners = testBusinessOwners.stream()
                .filter(bo -> bo.getFirstName().equals(firstName))
                .sorted(Comparator.comparing(BusinessOwner::getFirstName))
                .toList();

        when(mockBusinessOwnerRepository.getBusinessOwnerEntitiesByLastNameOrderByLastName(anyString()))
                .thenReturn(firstNameSameEntities);

        List<BusinessOwner> result = subject.getBusinessOwnerListByLastName(firstName);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(mockMapperService, times(2))
                .mapEntityToObject(any(BusinessOwnerEntity.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "    ",})
    void givenFirstNameNullOrEmpty_whenCalledToRetrieveListOfBusinessOwners_thenThrowBusinessOwnerException(
            String firstName) {
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerListByFirstName(firstName));

        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());

    }

    @Test
    void givenFirstName_whenCalledToRetrieveListOfBusinessOwnersErrorOccues_thenThrowBusinessOwnerException() {
        when(mockBusinessOwnerRepository.getBusinessOwnerEntitiesByFirstNameOrderByFirstName(anyString()))
                .thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerListByFirstName(FIRST_NAME_1));

        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenLastName_whenCalledToRetrieveListOfBusinessOwners_thenReturnBusinessOwnersWithTheLastName() throws BusinessOwnerException {
        String lastName = LAST_NAME_2;

        List<BusinessOwnerEntity> firstNameSameEntities = testBusinessOwnerEntities.stream()
                .filter(entity -> entity.getLastName().equals(lastName))
                .sorted(Comparator.comparing(BusinessOwnerEntity::getFirstName))
                .toList();

        List<BusinessOwner> firstNameBusinessOwners = testBusinessOwners.stream()
                .filter(bo -> bo.getLastName().equals(lastName))
                .sorted(Comparator.comparing(BusinessOwner::getFirstName))
                .toList();

        when(mockBusinessOwnerRepository.getBusinessOwnerEntitiesByFirstNameOrderByFirstName(anyString()))
                .thenReturn(firstNameSameEntities);

        List<BusinessOwner> result = subject.getBusinessOwnerListByFirstName(lastName);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(mockMapperService, times(2))
                .mapEntityToObject(any(BusinessOwnerEntity.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "    ",})
    void givenLastNameNullOrEmpty_whenCalledToRetrieveListOfBusinessOwners_thenThrowBusinessOwnerException(
            String lastName) {
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerListByLastName(lastName));

        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());

    }

    @Test
    void givenLastName_whenCalledToRetrieveListOfBusinessOwnersErrorOccurs_thenThrowBusinessOwnerException() {
        when(mockBusinessOwnerRepository.getBusinessOwnerEntitiesByLastNameOrderByLastName(anyString()))
                .thenThrow(new RuntimeException("someException"));
        BusinessOwnerException bex = assertThrows(BusinessOwnerException.class, () ->
                subject.getBusinessOwnerListByLastName(LAST_NAME_2));

        assertEquals(INTERNAL_SERVER_ERROR, bex.getHttpStatus());
    }

    @Test
    void givenValidBusinessOwnerId_whenCalledToDeleteBusinessOwnerFromDb_thenReturnTrue() throws BusinessOwnerException {
        doNothing().when(mockBusinessOwnerRepository).deleteBusinessOwnerEntityByBusinessId(anyLong());
        when(mockBusinessOwnerRepository.existsBusinessOwnerEntityByBusinessId(anyLong())).thenReturn(true);
        boolean result = subject.deleteBusinessOwnerById(1L);
        verify(mockBusinessOwnerRepository).deleteBusinessOwnerEntityByBusinessId(1L);
        assertTrue(result);
    }

    @Test
    void givenNonExistentBusinessOwnerId_whenCalledToDeleteBusinessOwnerFromDb_thenReturnFalse() throws BusinessOwnerException {
        doNothing().when(mockBusinessOwnerRepository).deleteBusinessOwnerEntityByBusinessId(anyLong());
        when(mockBusinessOwnerRepository.existsBusinessOwnerEntityByBusinessId(anyLong())).thenReturn(false);
        boolean result = subject.deleteBusinessOwnerById(5L);
        verify(mockBusinessOwnerRepository, times(0))
                .deleteBusinessOwnerEntityByBusinessId(5L);
        assertFalse(result);
    }

    @Test
    void givenSomeBusinessOwnerId_whenCalledToDeleteBusinessOwnerFromDbErrorOccurs_thenThrowBusinessOwnerException() {
        when(mockBusinessOwnerRepository.existsBusinessOwnerEntityByBusinessId(anyLong())).thenReturn(true);
        doThrow(new RuntimeException("someException")).when(mockBusinessOwnerRepository)
                .deleteBusinessOwnerEntityByBusinessId(anyLong());
        assertThrows(BusinessOwnerException.class, () -> subject.deleteBusinessOwnerById(1L));
    }

    @Test
    void givenBusinessOwnerId_whenCalledToDeleteBusinessOwnerFromDbErrorOccurs_thenThrowBusinessOwnerException() {
        when(mockBusinessOwnerRepository.existsBusinessOwnerEntityByBusinessId(anyLong()))
                .thenThrow(new RuntimeException("someException"));
        doNothing().when(mockBusinessOwnerRepository).deleteBusinessOwnerEntityByBusinessId(anyLong());
        assertThrows(BusinessOwnerException.class, () -> subject.deleteBusinessOwnerById(1L));
    }
}