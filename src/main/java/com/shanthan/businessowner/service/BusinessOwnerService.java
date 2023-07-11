package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.Address;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import com.shanthan.businessowner.repository.BusinessOwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.*;
import static java.util.Optional.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.ObjectUtils.*;

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
            businessOwner.setPhoneNumber(formatPhoneNumber(businessOwner.getPhoneNumber()));
            businessOwner.setSsn(formatSsn(businessOwner.getSsn()));
            BusinessOwnerEntity businessOwnerEntity = mapperService.mapObjectToEntity(businessOwner);
            businessOwnerEntity = businessOwnerRepository.saveAndFlush(businessOwnerEntity);
            return mapperService.mapEntityToObject(businessOwnerEntity);
        } catch (Exception ex) {
            log.error("Error while adding Business Owner [{}] ", businessOwner);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        log.info("Formatting phoneNumber to format -> (###) ###-####");
        return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "($1) $2-$3");
    }

    private String formatSsn(String ssn) {
        log.info("Formatting ssn to format -> ###-##-###");
        return ssn.replaceFirst("(\\d{3})(\\d{2})(\\d{4})", "$1-$2-$3");
    }

    public BusinessOwner updateBusinessOwner(BusinessOwner businessOwner) throws BusinessOwnerException {
        if (isEmpty(businessOwner.getBusinessId())) {
            log.error("Need business owner Id to update business owner ");
            throw new BusinessOwnerException(BAD_REQUEST, "Need business owner Id to update business owner. ");
        }
        BusinessOwnerEntity existingEntity;

        try {
            existingEntity =
                    businessOwnerRepository.getBusinessOwnerEntityByBusinessId(businessOwner.getBusinessId());
            log.info("Existing business owner -> [{}] ", existingEntity);

            String decryptedSsn = mapperService.decryptField(existingEntity.getSsn());
            String decryptedPhoneNumber = mapperService.decryptField(existingEntity.getPhoneNumber());
            String decryptedDobString = mapperService.decryptField(existingEntity.getDateOfBirth());
            Address address = mapperService.mapAddressStringToObject(existingEntity.getAddress());

            BusinessOwner updatedBusinessOwner = BusinessOwner.builder()
                    .businessId(businessOwner.getBusinessId())
                    .firstName(substituteIfEmpty(businessOwner.getFirstName(), existingEntity.getFirstName()))
                    .lastName(substituteIfEmpty(businessOwner.getLastName(), existingEntity.getLastName()))
                    .ssn(substituteIfEmpty(businessOwner.getSsn(), decryptedSsn))
                    .phoneNumber(substituteIfEmpty(businessOwner.getPhoneNumber(), decryptedPhoneNumber))
                    .dateOfBirth(substituteDateIfEmpty(businessOwner.getDateOfBirth(), parse(decryptedDobString)))
                    .address(substituteAddressIfEmpty(businessOwner.getAddress(), address))
                    .build();

            if (!doesBusinessOwnerWithIdExist(businessOwner.getBusinessId())) {
                log.info("Business owner with businessId -> {} does not exist", businessOwner.getBusinessId());
                return BusinessOwner.builder().build();
            }

            BusinessOwnerEntity updatedEntity = mapperService.mapObjectToEntity(updatedBusinessOwner);
            updatedEntity.setBusinessId(existingEntity.getBusinessId());
            updatedEntity = businessOwnerRepository.saveAndFlush(updatedEntity);
            return mapperService.mapEntityToObject(updatedEntity);

        } catch (Exception ex) {
            log.error("Error while updating Business Owner [{}] ", businessOwner);
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    private String substituteIfEmpty(String field, String substitute) {
        return field.isBlank() ? substitute : field;
    }

    private LocalDate substituteDateIfEmpty(LocalDate field, LocalDate substitute) {
        return isEmpty(field) ? substitute : field;
    }

    private Address substituteAddressIfEmpty(Address field, Address substitute) {

        return (field.getCity().isBlank()) ? substitute : field;
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

            if (isEmpty(entityList) || entityList.isEmpty()) {
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

            if (isEmpty(entityList) || entityList.isEmpty()) {
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
