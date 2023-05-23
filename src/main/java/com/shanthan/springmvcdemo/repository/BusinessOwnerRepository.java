package com.shanthan.springmvcdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessOwnerRepository extends JpaRepository<BusinessOwnerEntity, Long> {

    List<BusinessOwnerEntity> getBusinessOwnerEntitiesByFirstNameOrderByFirstName(String firstName);

    List<BusinessOwnerEntity> getBusinessOwnerEntitiesByLastNameOrderByLastName(String lastName);

    void deleteBusinessOwnerEntityByBusinessId(Long businessId);

    BusinessOwnerEntity getBusinessOwnerEntityByBusinessId(Long businessId);


}
