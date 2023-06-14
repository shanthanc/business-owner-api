package com.shanthan.businessowner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessOwnerRepository extends JpaRepository<BusinessOwnerEntity, Long> {

    List<BusinessOwnerEntity> getBusinessOwnerEntitiesByFirstNameOrderByFirstName(String firstName);

    List<BusinessOwnerEntity> getBusinessOwnerEntitiesByLastNameOrderByLastName(String lastName);

    boolean existsBusinessOwnerEntityByBusinessId(Long businessId);

    void deleteById(Long businessId);


    BusinessOwnerEntity getBusinessOwnerEntityByBusinessId(Long businessId);


}
