package com.shanthan.businessowner.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "business_owner")
@Data
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessOwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "business_id", updatable = false)
    private Long businessId;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;


    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "address")
    private String address;

    @NotEmpty
    @Column(name = "ssn")
    private String ssn;

    @NotEmpty
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @NotEmpty
    private String dateOfBirth;

}
