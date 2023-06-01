package com.shanthan.businessowner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    Long businessId;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    String firstName;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$")
    String lastName;

    @NotNull
    @Pattern(regexp = "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$\" \n" +
            "      + \"|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$\"\n" +
            "      + \"|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$\" \n" +
            "      + \"|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$")
    LocalDate dateOfBirth;

    Address address;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$")
    String phoneNumber;

    @NotEmpty
    @Pattern(regexp = "^\\\\d{3}-\\\\d{2}-\\\\d{4}")
    String ssn;

}
