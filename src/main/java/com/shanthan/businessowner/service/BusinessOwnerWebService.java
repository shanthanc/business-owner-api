package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Slf4j
public class BusinessOwnerWebService {

    public final BusinessOwnerService businessOwnerService;
    public final ValidationService validationService;

    public BusinessOwnerWebService(BusinessOwnerService businessOwnerService, ValidationService validationService) {
        this.businessOwnerService = businessOwnerService;
        this.validationService = validationService;
    }

    public BusinessOwner create(BusinessOwner businessOwner) throws BusinessOwnerException {

        BusinessOwner transformedBusinessOwner;
        try {
            businessOwner.setDateOfBirth(validationService.validDateOfBirth(businessOwner.getDob()));
            transformedBusinessOwner = businessOwnerService.addBusinessOwner(businessOwner);
        } catch (BusinessOwnerException ex) {
            log.error("Error while creating new business owner through web service call. ");
            throw new BusinessOwnerException(ex.getHttpStatus(), ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Error while creating new business owner through web service call. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return transformedBusinessOwner;
    }

    public BusinessOwner update(BusinessOwner businessOwner) throws BusinessOwnerException {

        BusinessOwner updatedBusinessOwner;
        try {
            if (!businessOwner.getDob().isBlank()) {
                LocalDate dateOfBirth = validationService.validDateOfBirth(businessOwner.getDob());
                businessOwner.setDateOfBirth(dateOfBirth);
            }
            log.info("Updating business owner with id -> {} ", businessOwner.getBusinessId());
            updatedBusinessOwner = businessOwnerService.updateBusinessOwner(businessOwner);

        } catch (BusinessOwnerException ex) {
            log.error("Error while creating new business owner through web service call. ");
            throw new BusinessOwnerException(ex.getHttpStatus(), ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Error while creating new business owner through web service call. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return updatedBusinessOwner;
    }


    public BusinessOwner getBusinessOwnerById(Long businessId) throws BusinessOwnerException {
        BusinessOwner businessOwner;
        try {
            if (!businessOwnerService.doesBusinessOwnerWithIdExist(businessId)) {
                throw new BusinessOwnerException(NOT_FOUND, "Business Owner with id -> " + businessId +
                        " does not exist ");
            }
            log.info("Retrieving details of business owner with id -> {} ", businessId);
            businessOwner = businessOwnerService.getBusinessOwnerByBusinessId(businessId);
        } catch (BusinessOwnerException ex) {
            log.error("Error retrieving businessOwner with id -> {} ", businessId);
            throw new BusinessOwnerException(ex.getHttpStatus(), ex.getMessage(), ex);
        } catch (Exception ex) {
            log.error("Error retrieving businessOwner with id -> {} ", businessId);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return businessOwner;
    }

    public boolean deleteBusinessOwner(Long businessId) throws BusinessOwnerException {
        boolean result;
        try {
            log.info("Deleting business owner with id -> {} ", businessId);
            result = businessOwnerService.deleteBusinessOwnerById(businessId);
        } catch (BusinessOwnerException bex) {
            log.error("Unable to delete businessOwner with id -> {} ", businessId);
            throw new BusinessOwnerException(bex.getHttpStatus(), bex.getMessage(), bex);
        } catch (Exception ex) {
            log.error("Unexpected error occurred while trying to delete business owner with id -> {} ", businessId);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return result;
    }

    public List<BusinessOwner> businessOwnerListByFirstName(String firstName) throws BusinessOwnerException {
        List<BusinessOwner> result;

        try {
            if (isEmpty(firstName) || firstName.isBlank()) {
                log.error("First name is null or empty. ");
                throw new BusinessOwnerException(BAD_REQUEST, "First name is blank. ");
            }
            result = businessOwnerService.getBusinessOwnerListByFirstName(firstName);
        } catch (BusinessOwnerException bex) {
            log.error("Error while retrieving business owner list by firstName -> {} ", firstName);
            throw new BusinessOwnerException(bex.getHttpStatus(), bex.getMessage(), bex);
        } catch (Exception ex) {
            log.error("Unexpected error while retrieving business owner list by firstName -> {} ", firstName);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return result;
    }

    public List<BusinessOwner> businessOwnerListByLastName(String lastName) throws BusinessOwnerException {
        List<BusinessOwner> result;

        try {
            if (isEmpty(lastName) || lastName.isBlank()) {
                log.error("Last name is null or empty. ");
                throw new BusinessOwnerException(BAD_REQUEST, "Last name is blank. ");
            }
            result = businessOwnerService.getBusinessOwnerListByLastName(lastName);
        } catch (BusinessOwnerException bex) {
            log.error("Error while retrieving business owner list by lastName -> {} ", lastName);
            throw new BusinessOwnerException(bex.getHttpStatus(), bex.getMessage(), bex);
        } catch (Exception ex) {
            log.error("Unexpected error while retrieving business owner list by lastName -> {} ", lastName);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return result;
    }
}
