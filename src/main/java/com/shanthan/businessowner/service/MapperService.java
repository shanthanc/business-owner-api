package com.shanthan.businessowner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.Address;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
public class MapperService {

    private final ObjectMapper objectMapper;
    private final EncryptionService encryptionService;
    public MapperService(ObjectMapper objectMapper, EncryptionService encryptionService) {

        this.objectMapper = objectMapper;
        this.encryptionService = encryptionService;
    }

    public Address mapAddressStringToObject(String addressString) throws BusinessOwnerException {
        Address address;
        try {
            address = objectMapper.readValue(addressString, Address.class);
        } catch (Exception ex) {
            log.error("Error mapping Address String to object! ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return address;
    }

    public String mapAddressObjectToString(Address address) throws BusinessOwnerException {
        String addressString;
        try {
            addressString = objectMapper.writeValueAsString(address);
        } catch (Exception ex) {
            log.error("Error while mapping Address Object to String! ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return addressString;

    }

    public String encryptField(String field) throws BusinessOwnerException {
        String encyrptedField;
        if(!field.isBlank()) {
            try {
                log.info("Encrypting given field ");
                encyrptedField = encryptionService.encrypt(field);

            } catch (Exception e) {
                log.error("Error while encrypting given field! ");
                throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
            }
        } else {
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, "field is null. Cannot proceed. ");
        }
        return encyrptedField;
    }

    public String encryptDob(LocalDate localDate) throws BusinessOwnerException {
        if(ObjectUtils.isEmpty(localDate)) {
            log.error("Date of birth is null! ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, "Date Of birth is null. Cannot proceed. ");
        }
        String encryptedDob;
        try {
            encryptedDob = encryptionService.encrypt(localDate.format(ISO_LOCAL_DATE));
        } catch (Exception e) {
            log.error("Error while encrypting Date of birth. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return encryptedDob;
    }

    public String decryptField(String encryptedField) throws BusinessOwnerException {
        if(encryptedField.isBlank()) {
            log.error("Given field to decrypt is null. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR,
                    "Given field to decrypt is null. Cannot proceed. " );
        }
        String decryptedField;
        try {
            decryptedField = encryptionService.decrypt(encryptedField);

        } catch (Exception e) {
            log.error("Error while decrypting field. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return decryptedField;
    }

    public LocalDate decryptDob(String localDate) throws BusinessOwnerException {
        if(localDate.isBlank()) {
            log.error("Given encryptedDob to decrypt is null. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR,
                    "Given encryptedDob to decrypt is null. Cannot proceed. " );
        }
        LocalDate decryptedDate;
        try {
            String decryptedDt = encryptionService.decrypt(localDate);
            decryptedDate = LocalDate.parse(decryptedDt, ISO_LOCAL_DATE);
        } catch (Exception ex) {
            log.error("Error while decrypting date of birth field. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return decryptedDate;
    }

    public BusinessOwner mapEntityToObject(BusinessOwnerEntity businessOwnerEntity) throws BusinessOwnerException {
        log.info("Mapping businessOwnerEntity with Id {} to businessOwner ", businessOwnerEntity.getBusinessId());
        BusinessOwner businessOwner;

        try {
            businessOwner = BusinessOwner.builder()
                    .businessId(businessOwnerEntity.getBusinessId())
                    .firstName(businessOwnerEntity.getFirstName())
                    .lastName(businessOwnerEntity.getLastName())
                    .address(mapAddressStringToObject(businessOwnerEntity.getAddress()))
                    .ssn(decryptField(businessOwnerEntity.getSsn()))
                    .phoneNumber(decryptField(businessOwnerEntity.getPhoneNumber()))
                    .dateOfBirth(decryptDob(businessOwnerEntity.getDateOfBirth()))
                    .build();
        } catch (Exception ex) {
            log.error("Error when mapping BusinessOwnerEntity to Object. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return businessOwner;
    }

    public BusinessOwnerEntity mapObjectToEntity(BusinessOwner businessOwner) throws BusinessOwnerException {
        log.info("Mapping given businessOwner with Id [{}] to businessOwnerEntity", businessOwner);
        BusinessOwnerEntity businessOwnerEntity = null;

        try {
            businessOwnerEntity = BusinessOwnerEntity.builder()
                    .firstName(businessOwner.getFirstName())
                    .lastName(businessOwner.getLastName())
                    .address(mapAddressObjectToString(businessOwner.getAddress()))
                    .ssn(encryptField(businessOwner.getSsn()))
                    .phoneNumber(encryptField(businessOwner.getPhoneNumber()))
                    .dateOfBirth(encryptDob(businessOwner.getDateOfBirth()))
                    .build();

        } catch (Exception ex) {
            log.error("Error when mapping BusinessOwner Object to Entity. ");
            throw new BusinessOwnerException(INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
        return businessOwnerEntity;
    }

}
