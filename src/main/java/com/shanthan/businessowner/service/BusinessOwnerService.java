package com.shanthan.businessowner.service;

import com.shanthan.businessowner.controller.BusinessOwnerController;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.repository.BusinessOwnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;

    public BusinessOwnerService(BusinessOwnerRepository businessOwnerRepository) {
        this.businessOwnerRepository = businessOwnerRepository;
    }

    public BusinessOwner getBusinessOwnerByBusinessIdFromDb(Long businessId) {
        return null;
    }
}
