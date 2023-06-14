package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import com.shanthan.businessowner.repository.BusinessOwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;

    private final MapperService mapperService;

    public BusinessOwnerService(BusinessOwnerRepository businessOwnerRepository,
                                MapperService mapperService) {
        this.businessOwnerRepository = businessOwnerRepository;
        this.mapperService = mapperService;
    }

    public BusinessOwner addBusinessOwner(BusinessOwner businessOwner) throws BusinessOwnerException {
        try {
            BusinessOwnerEntity businessOwnerEntity = mapperService.mapObjectToEntity(businessOwner);
            businessOwnerEntity = businessOwnerRepository.saveAndFlush(businessOwnerEntity);
            return mapperService.mapEntityToObject(businessOwnerEntity);
        } catch (Exception ex) {
            log.error("Error while adding Business Owner [{}] ", businessOwner);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    public BusinessOwner updateBusinessOwner(BusinessOwner businessOwner) throws BusinessOwnerException {
        if (ObjectUtils.isEmpty(businessOwner.getBusinessId())) {
            log.error("Need business owner Id to update business owner ");
            throw new BusinessOwnerException(BAD_REQUEST, "Need business owner Id to update business owner. ");
        }
        try {
            BusinessOwner updatedBusinessOwner = BusinessOwner.builder()
                    .businessId(businessOwner.getBusinessId())
                    .firstName(businessOwner.getFirstName())
                    .lastName(businessOwner.getLastName())
                    .ssn(businessOwner.getSsn())
                    .phoneNumber(businessOwner.getPhoneNumber())
                    .dateOfBirth(businessOwner.getDateOfBirth())
                    .address(businessOwner.getAddress())
                    .build();
            if (!doesBusinessOwnerWithIdExist(businessOwner.getBusinessId())) {
                log.info("Business owner with businessId -> {} does not exist", businessOwner.getBusinessId());
                return BusinessOwner.builder().build();
            }
            BusinessOwnerEntity existingBusinessOwnerEntity =
                    businessOwnerRepository.getBusinessOwnerEntityByBusinessId(businessOwner.getBusinessId());
            BusinessOwnerEntity updatedEntity = mapperService.mapObjectToEntity(updatedBusinessOwner);
            updatedEntity.setBusinessId(existingBusinessOwnerEntity.getBusinessId());
            updatedEntity = businessOwnerRepository.saveAndFlush(updatedEntity);
            return mapperService.mapEntityToObject(updatedEntity);
        } catch (Exception ex) {
            log.error("Error while adding Business Owner [{}] ", businessOwner);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    public BusinessOwner getBusinessOwnerByBusinessId(Long businessId) throws BusinessOwnerException {
        BusinessOwner businessOwner;
        try {
            log.info("Retrieving BusinessOwner with Id {} from Db ", businessId);
            BusinessOwnerEntity businessOwnerEntity =
                    businessOwnerRepository.getBusinessOwnerEntityByBusinessId(businessId);
            businessOwner = mapperService.mapEntityToObject(businessOwnerEntity);
        } catch (Exception ex) {
            log.error("Error occurred while retrieving BusinessOwner details from Db ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return businessOwner;

    }

    public List<BusinessOwner> getBusinessOwnerListByFirstName(String firstName) throws BusinessOwnerException {
        if (firstName.isBlank()) {
            log.error("First name is null or empty. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, "First name is null or empty. Cannot proceed.");
        }
        List<BusinessOwner> businessOwners;
        try {
            List<BusinessOwnerEntity> entityList =
                    businessOwnerRepository.getBusinessOwnerEntitiesByFirstNameOrderByFirstName(firstName);

            if (ObjectUtils.isEmpty(entityList) || entityList.isEmpty()) {
                return Collections.emptyList();
            }

            businessOwners = new ArrayList<>();
            for (BusinessOwnerEntity entity : entityList) {
                businessOwners.add(mapperService.mapEntityToObject(entity));
            }
        } catch (Exception e) {
            log.error("Error while retrieving list of business owner by first name ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return businessOwners;
    }

    public List<BusinessOwner> getBusinessOwnerListByLastName(String lastName) throws BusinessOwnerException {
        if (lastName.isBlank()) {
            log.error("Last name is null or empty. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, "Last name is null or empty. Cannot proceed.");
        }
        List<BusinessOwner> businessOwners;
        try {
            List<BusinessOwnerEntity> entityList =
                    businessOwnerRepository.getBusinessOwnerEntitiesByLastNameOrderByLastName(lastName);

            if (ObjectUtils.isEmpty(entityList) || entityList.isEmpty()) {
                return Collections.emptyList();
            }

            businessOwners = new ArrayList<>();
            for (BusinessOwnerEntity entity : entityList) {
                businessOwners.add(mapperService.mapEntityToObject(entity));
            }
        } catch (Exception e) {
            log.error("Error while retrieving list of business owner by last name ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return businessOwners;
    }

    public boolean deleteBusinessOwnerById(Long id) throws BusinessOwnerException {
        log.info("Deleting business owner with id {} ", id);
        try {
            if (!businessOwnerRepository.existsBusinessOwnerEntityByBusinessId(id)) {
                return false;
            }
            businessOwnerRepository.deleteById(id);
        } catch (Exception ex) {
            log.error("Error while trying to delete Business Owner from Db ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return true;
    }

    public boolean doesBusinessOwnerWithIdExist(Long businessId) throws BusinessOwnerException {
        log.info("Checking if BusinessOwner with id -> {} exist ", businessId);
        try {
            return businessOwnerRepository.existsBusinessOwnerEntityByBusinessId(businessId);
        } catch (Exception ex) {
            log.error("Error occurred while checking if businessOwner with id -> {} exists ", businessId);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }

    }
}
