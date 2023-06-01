package com.shanthan.businessowner.testutils;

import com.shanthan.businessowner.model.Address;

import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class TestConstants {

    private TestConstants() {
        //disable instantiation
    }

    public static final String FIRST_NAME_1 = "someFirstName1";

    public static final String LAST_NAME_1 = "someLastName1";

    //language=Json
    public static final String ADDRESS_1_STRING = "{\n" +
            "  \"addressLine1\" : \"456 Some St\",\n" +
            "  \"addressLine2\" : \"Apt 2\",\n" +
            "  \"city\" : \"Chicago\",\n" +
            "  \"state\" : \"IL\",\n" +
            "  \"zipcode\" : \"60676\"\n" +
            "}";

    public static final Address ADDRESS_OBJECT_1 = Address.builder()
            .addressLine1("456 Some St")
            .addressLine2("Apt 2")
            .city("Chicago")
            .state("IL")
            .zipcode("60676")
            .build();
    public static final String ADDRESS_CITY_1 = "Chicago";

    public static final String ADDRESS_ZIPCODE_1 = "60676";

    public static final String SOME_SSN = "123-45-6789";

    public static final String SOME_PHONE = "123-456-7890";

    public static final LocalDate SOME_DOB = LocalDate.of(1995, 1, 1);

    public static final String SOME_DOB_STRING = SOME_DOB.format(ISO_LOCAL_DATE);
    public static final String ENCYRPTED_SSN_1 = "someEncryptedSsn1";

    public static final String ENCRYPTED_PHONE_NUMBER_1 = "someEncryptedPhoneNumber1";

    public static final String ENCRYPTED_DOB_1 = "someEncryptedDob1";

    public static final String SOME_ENCRYPTED_STRING = "someEncryptedString";

    public static final String SOME_DECRYPTED_STRING = "someDecryptedString";
}
