package com.shanthan.businessowner.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shanthan.businessowner.util.CustomDateDeserializer;
import com.shanthan.businessowner.util.CustomDateSerializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusinessOwner {

    private Long businessId;

    @NotEmpty
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s'`-]+$",
            message = "Must be valid name with no numbers or special characters except - '`")
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 50, message = "Must be a valid length between 2 and 50")
    @Pattern(regexp = "^[a-zA-Z\\s'`-]+$",
            message = "Must be valid name with no numbers or special characters except - '`")
    private String lastName;

    @NotNull
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])\\-((19|20)\\d\\d)$",
    message = "Must be a valid date of birth string of format -> 'MM-dd-yyyy' ")
    private String dob;

    private Address address;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Must be a valid phone number whole string length is precisely 10 digits")
    private String phoneNumber;

    @NotEmpty
    @Pattern(regexp = "^\\d{9}", message = "Must be a valid ssn whole string length is precisely 9 digits")
    private String ssn;

}
