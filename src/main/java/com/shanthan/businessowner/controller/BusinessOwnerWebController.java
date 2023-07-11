package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.Address;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.model.Success;
import com.shanthan.businessowner.service.BusinessOwnerWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shanthan.businessowner.util.BusinessOwnerConstants.*;

@Controller
@RequestMapping("/web")
@Slf4j
public class BusinessOwnerWebController {

    public final BusinessOwnerWebService businessOwnerWebService;

    public BusinessOwnerWebController(BusinessOwnerWebService businessOwnerWebService) {
        this.businessOwnerWebService = businessOwnerWebService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute(STATUS_MESSAGE_ATTRIBUTE, HOME_INDEX_STATUS_MESSAGE);
        return HOME_PAGE;
    }

    @GetMapping
    private String error(Model model) {
        return ERROR_PAGE;
    }

    @GetMapping("/addBusinessOwner")
    public String addBusinessOwner(Model model) {
        Address address = Address.builder().build();
        BusinessOwner businessOwner = BusinessOwner.builder().build();
        businessOwner.setAddress(address);
        model.addAttribute(ADDRESS_ATTRIBUTE, address);
        model.addAttribute(BUSINESS_OWNER_ATTRIBUTE, businessOwner);
        model.addAttribute(IS_UPDATE_ATTRIBUTE, false);
        return CREATE_UPDATE_PAGE;
    }

    @GetMapping("/updateBusinessOwner")
    public String updateBusinessOwner(Model model) {
        Address address = Address.builder().build();
        BusinessOwner businessOwner = BusinessOwner.builder().build();
        businessOwner.setAddress(address);
        model.addAttribute(ADDRESS_ATTRIBUTE, address);
        model.addAttribute(BUSINESS_OWNER_ATTRIBUTE, businessOwner);
        model.addAttribute(IS_UPDATE_ATTRIBUTE, true);
        return CREATE_UPDATE_PAGE;
    }

    @PostMapping("/create")
    public String createBusinessOwner(Model model, @ModelAttribute(ADDRESS_ATTRIBUTE) Address address,
                                      @ModelAttribute(BUSINESS_OWNER_ATTRIBUTE) BusinessOwner businessOwner)
            throws BusinessOwnerException {
        businessOwner.setAddress(address);
        BusinessOwner newBusinessOwner = businessOwnerWebService.create(businessOwner);
        Success success = Success.builder()
                .message1(BUSINESS_OWNER_CREATION_SUCCESS_1)
                .message2(newBusinessOwner.getBusinessId().toString())
                .message3(BUSINESS_OWNER_CREATION_SUCCESS_2)
                .build();
        model.addAttribute("successMessage", success.getMessage1().concat(success.getMessage2())
                .concat(success.getMessage3()));
        return SUCCESS_PAGE;
    }

    @PostMapping("/update")
    public String updateBusinessOwner(Model model, @ModelAttribute(ADDRESS_ATTRIBUTE) Address address,
                                      @ModelAttribute(BUSINESS_OWNER_ATTRIBUTE) BusinessOwner businessOwner)
            throws BusinessOwnerException {
        businessOwner.setAddress(address);
        BusinessOwner updatedBusinessOwner = businessOwnerWebService.update(businessOwner);
        Success success = Success.builder()
                .message1(BUSINESS_OWNER_CREATION_SUCCESS_1)
                .message2(updatedBusinessOwner.getBusinessId().toString())
                .message3(BUSINESS_OWNER_UPDATED_SUCCESS_2)
                .build();
        model.addAttribute("successMessage", success.getMessage1().concat(success.getMessage2())
                .concat(success.getMessage3()));
        return SUCCESS_PAGE;
    }

    @GetMapping("/getBusinessOwnerById")
    public String getBusinessOwnerById(Model model) {
        return "get-business-owner-by-id";
    }

    @GetMapping("/businessOwnerDetails")
    public String getBusinessOwner(@RequestParam Long businessId, Model model) throws BusinessOwnerException {
        BusinessOwner businessOwner = businessOwnerWebService.getBusinessOwnerById(businessId);
        model.addAttribute(BUSINESS_OWNER_ATTRIBUTE, businessOwner);
        return "business-owner-details";
    }

    @GetMapping("/deleteBusinessOwnerById")
    public String deleteBusinessOwnerById(Model model) {
        return "delete-business-owner-id";
    }

    @PostMapping("/deleteBusinessOwner")
    public String deleteBusinessOwner(@RequestParam("businessId") Long businessId, Model model)
            throws BusinessOwnerException {
        boolean result = businessOwnerWebService.deleteBusinessOwner(businessId);
        if (result) {
            model.addAttribute(SUCCESS_MESSAGE_ATTRIBUTE,
                    "Deletion of business owner with id -> " + businessId + " successful. ");

            return SUCCESS_PAGE;
        } else {
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE,
                    "Business owner with id -> " + businessId + " requested to delete does not " +
                            "exist.");
            return ERROR_PAGE;
        }
    }

    @GetMapping("/getBusinessOwnersByFirstName")
    public String getBusinessOwnersByFirstName(Model model) {
        return "get-list-by-first-name";
    }

    @GetMapping("/businessOwnersByFirstName")
    public String listBusinessOwnersByFirstName(@RequestParam("firstName") String firstName, Model model)
            throws BusinessOwnerException {
        List<BusinessOwner> businessOwners = businessOwnerWebService.businessOwnerListByFirstName(firstName);
        if (businessOwners.isEmpty()) {
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE,
                    "No business owners present with first name -> " + firstName);
            return ERROR_PAGE;
        }
        model.addAttribute("businessOwners", businessOwners);
        return "list-business-owners";
    }

    @GetMapping("/getBusinessOwnersByLastName")
    public String getBusinessOwnersByLastName(Model model) {
        return "get-list-by-last-name";
    }

    @GetMapping("/businessOwnersByLastName")
    public String listBusinessOwnersByLastName(@RequestParam("lastName") String lastName, Model model)
            throws BusinessOwnerException {
        List<BusinessOwner> businessOwners = businessOwnerWebService.businessOwnerListByLastName(lastName);
        if (businessOwners.isEmpty()) {
            model.addAttribute(ERROR_MESSAGE_ATTRIBUTE,
                    "No business owners present with last name -> " + lastName);
            return ERROR_PAGE;
        }
        model.addAttribute("businessOwners", businessOwners);
        return "list-business-owners";
    }
}

