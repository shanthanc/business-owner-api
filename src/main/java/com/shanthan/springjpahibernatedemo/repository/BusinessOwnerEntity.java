package com.shanthan.springjpahibernatedemo.repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "business_owner")
@Data
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
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    private String firstName;


    @NotEmpty
    @Column(name = "last_name")
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
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
