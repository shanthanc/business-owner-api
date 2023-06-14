package com.shanthan.businessowner.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@Sql(scripts = "classpath:test-data/insertData.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:test-data/cleanup.sql", executionPhase = AFTER_TEST_METHOD)
class BusinessOwnerRepositoryTest {

    @Autowired
    private BusinessOwnerRepository subject;

    @Test
    void test_ForChecking_GetBusinessOwnerEntitiesByBusinessId_FromDb() {
        BusinessOwnerEntity businessOwnerEntity = subject.getBusinessOwnerEntityByBusinessId(1L);
        assertEquals("John", businessOwnerEntity.getFirstName());
        assertEquals("Doe", businessOwnerEntity.getLastName());
    }

    @Test
    void test_ForChecking_GetBusinessOwnerEntitiesByFirstNameOrderByFirstName_FromDb() {
        List<BusinessOwnerEntity> businessOwnerEntities =
                subject.getBusinessOwnerEntitiesByFirstNameOrderByFirstName("Sarah");
        assertEquals(2, businessOwnerEntities.size());
    }

    @Test
    void test_ForChecking_GetBusinessOwnerEntitiesByLastNameOrderByLastName_FromDb() {
        List<BusinessOwnerEntity> businessOwnerEntities =
                subject.getBusinessOwnerEntitiesByLastNameOrderByLastName("Durant");
        assertEquals(2, businessOwnerEntities.size());
    }

    @Test
    void test_ForChecking_IfBusinessOwnerEntityExists_ByBusinessId_InDb() {
        boolean resultTrue = subject.existsBusinessOwnerEntityByBusinessId(3L);
        boolean resultFalse = subject.existsBusinessOwnerEntityByBusinessId(10L);
        assertTrue(resultTrue);
        assertFalse(resultFalse);
    }
    @Test
    void test_ForChecking_UpdateBusinessOwnerEntityAttributes_ByBusinessId_FromDb() {
        BusinessOwnerEntity businessOwnerEntity = subject.getBusinessOwnerEntityByBusinessId(1L);
        System.out.println("businessOwnerEntity");
        businessOwnerEntity.setFirstName("Johnson");
        subject.saveAndFlush(businessOwnerEntity);
        BusinessOwnerEntity updatedEntity = subject.getBusinessOwnerEntityByBusinessId(1L);
        List<BusinessOwnerEntity> all = subject.findAll();
        assertEquals(5, all.size());
        assertEquals("Johnson", updatedEntity.getFirstName());

    }

    @Test
    void test_ForChecking_deleteBusinessOwnerEntityByBusinessId_FromDb() {
        BusinessOwnerEntity entity = subject.getBusinessOwnerEntityByBusinessId(2L);
        assertNotNull(entity);
        subject.deleteById(2L);
        BusinessOwnerEntity businessOwnerEntity = subject.getBusinessOwnerEntityByBusinessId(2L);
        assertNull(businessOwnerEntity);

    }
}
