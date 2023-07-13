package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.Address;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.service.BusinessOwnerWebService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.shanthan.businessowner.testutils.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BusinessOwnerWebControllerTest {

    @InjectMocks
    private BusinessOwnerWebController subject;

    @Mock
    private BusinessOwnerWebService mockBusinessOwnerWebService;


    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    private BusinessOwner testBusinessOwner1;

    private BusinessOwner testBusinessOwner2;

    private BusinessOwner testBusinessOwner3;

    private List<BusinessOwner> testBusinessOwners;

    private BusinessOwner updatedBusinessOwner1;

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

        testBusinessOwner2 = BusinessOwner.builder()
                .businessId(2L)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .address(ADDRESS_OBJECT_2)
                .ssn(SOME_SSN_2)
                .phoneNumber(SOME_PHONE_2)
                .dob(SOME_DOB_2.toString())
                .build();

        testBusinessOwner3 = BusinessOwner.builder()
                .businessId(3L)
                .firstName(FIRST_NAME_3)
                .lastName(LAST_NAME_3)
                .address(ADDRESS_OBJECT_3)
                .ssn(SOME_SSN_3)
                .phoneNumber(SOME_PHONE_3)
                .dob(SOME_DOB_3.toString())
                .build();

        updatedBusinessOwner1 = BusinessOwner.builder()
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
    void givenRequestToGetHomePage_whenRequestMade_retrieveHomePage() {
        String result = subject.index(model);
        verify(model).addAttribute(eq(STATUS_MESSAGE_ATTRIBUTE),eq(HOME_INDEX_STATUS_MESSAGE));

        assertEquals("index", result);
    }

    @Test
    void givenARequestIsMade_whenErrorOccurs_thenReturnErrorPage() {
        String result = subject.error(model);

        assertEquals(ERROR_PAGE, result);
    }

    @Test
    void givenARequest_whenRequestForAddBusinessOwner_thenReturnCreateUpdatePage() {
        String result = subject.addBusinessOwner(model);
        Address address = Address.builder().build();
        BusinessOwner businessOwner = BusinessOwner.builder().build();
        businessOwner.setAddress(address);
        verify(model).addAttribute(ADDRESS_ATTRIBUTE, address);
        verify(model).addAttribute(BUSINESS_OWNER_ATTRIBUTE, businessOwner);
        verify(model).addAttribute(IS_UPDATE_ATTRIBUTE, false);

        assertEquals(CREATE_UPDATE_PAGE, result);
    }

    @Test
    void givenARequest_whenRequestForUpdateBusinessOwner_thenReturnCreateUpdatePage() {
        String result = subject.updateBusinessOwner(model);
        Address address = Address.builder().build();
        BusinessOwner businessOwner = BusinessOwner.builder().build();
        businessOwner.setAddress(address);
        verify(model).addAttribute(ADDRESS_ATTRIBUTE, address);
        verify(model).addAttribute(BUSINESS_OWNER_ATTRIBUTE, businessOwner);
        verify(model).addAttribute(IS_UPDATE_ATTRIBUTE, true);

        assertEquals(CREATE_UPDATE_PAGE, result);
    }



    @Test
    void givenBusinessOwnerOnWeb_whenCalledToCreateBusinessOwner_ThenReturnSuccessPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.create(any(BusinessOwner.class))).thenReturn(testBusinessOwner1);
        String result = subject.createBusinessOwner(model, ADDRESS_OBJECT_1, testBusinessOwner1);

        assertEquals(result, SUCCESS_PAGE);

    }

    @Test
    void givenBusinessOwnerOnWeb_whenCalledToUpdateBusinessOwner_ThenReturnSuccessPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.update(any(BusinessOwner.class))).thenReturn(updatedBusinessOwner1);
        String result = subject.updateBusinessOwner(model, ADDRESS_OBJECT_1, testBusinessOwner1);

        assertEquals(result, SUCCESS_PAGE);

    }

    @Test
    void givenBusinessOwnerOnWeb_whenCalledToUpdateBusinessOwner_ThenReturnCreateUpdatePage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.update(any(BusinessOwner.class))).thenReturn(testBusinessOwner1);
        String result = subject.updateBusinessOwner(model);
        verify(model).addAttribute(eq(ADDRESS_ATTRIBUTE), eq(Address.builder().build()));
        verify(model).addAttribute(eq(BUSINESS_OWNER_ATTRIBUTE), eq(BusinessOwner.builder()
                .address(Address.builder().build())
                .build()));
        verify(model).addAttribute(eq(IS_UPDATE_ATTRIBUTE), eq(true));
        assertEquals(result, CREATE_UPDATE_PAGE);
    }

    @Test
    void givenRequestToGetGetBusinessOwnerByIdPage_whenRequestMade_thenReturnGetBusinessOwnerByIdPage() {
        String result = subject.getBusinessOwnerById(model);
        assertEquals(result, "get-business-owner-by-id");
    }

    @Test
    void givenRequestForBusinessOwnerDetails_whenRequestMade_thenReturnBusinessOwnerDetailsPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.getBusinessOwnerById(anyLong())).thenReturn(testBusinessOwner1);
        String result = subject.getBusinessOwner(1L, model);
        verify(model).addAttribute(BUSINESS_OWNER_ATTRIBUTE, testBusinessOwner1);
        assertEquals("business-owner-details", result);
    }

    @Test
    void givenExistingBusinessIdToDelete_whenCalledToDeleteBusinessOwner_ThenReturnPageToEnterId() {
       String result = subject.deleteBusinessOwnerById(model);
       assertEquals("delete-business-owner-id", result);
    }

    @Test
    void givenExistingBusinessIdToDelete_whenIdEnteredDeleteBusinessOwnerAndDeleteSuccess_ThenReturnSuccessPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.deleteBusinessOwner(anyLong())).thenReturn(true);
        String result = subject.deleteBusinessOwner(1L, model);
        verify(model).addAttribute(SUCCESS_MESSAGE_ATTRIBUTE, DELETION_SUCCESS);

        assertEquals(SUCCESS_PAGE, result);
    }

    @Test
    void givenNonExistentBusinessIdToDelete_whenIdEnteredDeleteBusinessOwnerAndDeleteFails_ThenReturnErrorPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.deleteBusinessOwner(anyLong())).thenReturn(false);
        String result = subject.deleteBusinessOwner(1L, model);
        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE, DELETION_FAILED);

        assertEquals(ERROR_PAGE, result);
    }

    @Test
    void givenRequestToRenderPageToEnterFirstName_whenRequestMade_returnPageToEnterFirstName() {
        String result = subject.getBusinessOwnersByFirstName(model);
        assertEquals("get-list-by-first-name", result);
    }

    @Test
    void givenRequestWithFirstName_whenRequestMadeToGetBusinessOwnerList_thenReturnPageListingThem()
            throws BusinessOwnerException {

        List<BusinessOwner> firstNameList = testBusinessOwners.stream()
                .filter(bo -> bo.getFirstName().equals(FIRST_NAME_1))
                .toList();

        when(mockBusinessOwnerWebService.businessOwnerListByFirstName(anyString()))
                .thenReturn(firstNameList);

        String result = subject.listBusinessOwnersByFirstName(FIRST_NAME_1, model);

        verify(model).addAttribute("businessOwners", firstNameList);

        assertEquals("list-business-owners", result);
    }

    @Test
    void givenRequestWithFirstName_whenEmptyBusinessOwnerListReceived_thenReturnErrorPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.businessOwnerListByFirstName(anyString()))
                .thenReturn(Collections.emptyList());

        String result = subject.listBusinessOwnersByFirstName(FIRST_NAME_1, model);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE,
                "No business owners present with first name -> " + FIRST_NAME_1);

        assertEquals(ERROR_PAGE, result);
    }

    @Test
    void givenRequestToRenderPageToEnterLastName_whenRequestMade_returnPageToEnterLastName() {
        String result = subject.getBusinessOwnersByLastName(model);
        assertEquals("get-list-by-last-name", result);
    }

    @Test
    void givenRequestWithLastName_whenRequestMadeToGetBusinessOwnerList_thenReturnPageListingThem()
            throws BusinessOwnerException {
        List<BusinessOwner> lastNameList = testBusinessOwners.stream()
                .filter(bo -> bo.getLastName().equals(LAST_NAME_2))
                .toList();

        when(mockBusinessOwnerWebService.businessOwnerListByLastName(anyString()))
                .thenReturn(lastNameList);

        String result = subject.listBusinessOwnersByLastName(LAST_NAME_2, model);

        verify(model).addAttribute("businessOwners", lastNameList);

        assertEquals("list-business-owners", result);
    }

    @Test
    void givenRequestWithLastName_whenEmptyBusinessOwnerListReceived_thenReturnErrorPage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.businessOwnerListByLastName(anyString()))
                .thenReturn(Collections.emptyList());

        String result = subject.listBusinessOwnersByLastName(LAST_NAME_2, model);

        verify(model).addAttribute(ERROR_MESSAGE_ATTRIBUTE,
                "No business owners present with last name -> " + LAST_NAME_2);

        assertEquals(ERROR_PAGE, result);
    }
}