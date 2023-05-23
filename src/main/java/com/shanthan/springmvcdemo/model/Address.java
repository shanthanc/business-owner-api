package com.shanthan.springmvcdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {


    @NotEmpty
    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @NotEmpty
    @Column(name = "city")
    private String city;

    @NotEmpty
    @Column(name = "state")
    private String state;

    @NotEmpty
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$")
    @Column(name = "zipcode")
    private String zipcode;

}
