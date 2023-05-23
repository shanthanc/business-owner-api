package com.shanthan.springjpahibernatedemo.model;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
public class BusinessOwner {

    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    String firstName;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    String lastName;

    @NotNull
    @Pattern(regexp = "^(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])-(19|20)\\\\d\\\\d$")
    LocalDate dateOfBirth;

    Address address;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$")
    String phoneNumber;

    @NotEmpty
    @Pattern(regexp = "^\\\\d{3}-\\\\d{2}-\\\\d{4}")
    String ssn;

}
