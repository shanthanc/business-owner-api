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

        updatedBusinessOwner1 = BusinessOwner.builder()
                .businessId(1L)
                .firstName(UPDATED_FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .address(ADDRESS_OBJECT_1)
                .ssn(SOME_SSN_1)
                .phoneNumber(SOME_PHONE_1)
                .dateOfBirth(SOME_DOB_1)
                .build();
    }

    @Test
    void givenRequestToGetHomePage_whenRequestMade_retrieveHomePage() {
        String result = subject.index(model);
        verify(model).addAttribute(eq(STATUS_MESSAGE_ATTRIBUTE),eq(HOME_INDEX_STATUS_MESSAGE));
        assertEquals("index", result);
    }

    @Test
    void givenBusinessOwnerOnWeb_whenCalledToCreateBusinessOwner_ThenReturnCreateUpdatePage()
            throws BusinessOwnerException {
        when(mockBusinessOwnerWebService.create(any(BusinessOwner.class))).thenReturn(testBusinessOwner1);
        String result = subject.addBusinessOwner(model);
        verify(model).addAttribute(eq(ADDRESS_ATTRIBUTE), eq(Address.builder().build()));
        verify(model).addAttribute(eq(BUSINESS_OWNER_ATTRIBUTE), eq(BusinessOwner.builder()
                .address(Address.builder().build())
                .build()));
        verify(model).addAttribute(eq(IS_UPDATE_ATTRIBUTE), eq(false));
        assertEquals(result, CREATE_UPDATE_PAGE);

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
}