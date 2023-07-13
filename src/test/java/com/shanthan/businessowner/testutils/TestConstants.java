package com.shanthan.businessowner.testutils;

import com.shanthan.businessowner.model.Address;
import freemarker.core.Environment;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

import static java.time.LocalDate.*;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

public class TestConstants {

    private TestConstants() {
        //disable instantiation
    }

    public static final String FIRST_NAME_1 = "someFirstName1";

    public static final String UPDATED_FIRST_NAME_1 = "someUpdatedFirstName1";

    public static final String FIRST_NAME_2 = "someFirstName1";

    public static final String FIRST_NAME_3 = "someFirstName2";

    public static final String LAST_NAME_1 = "someLastName1";

    public static final String LAST_NAME_2 = "someLastName2";

    public static final String LAST_NAME_3 = "someLastName2";

    //language=Json
    public static final String ADDRESS_1_STRING = "{\n" +
            "  \"addressLine1\" : \"123 Some St\",\n" +
            "  \"addressLine2\" : \"Apt 1\",\n" +
            "  \"city\" : \"Chicago\",\n" +
            "  \"state\" : \"IL\",\n" +
            "  \"zipcode\" : \"60676\"\n" +
            "}";

    //language=Json
    public static final String ADDRESS_2_STRING = "{\n" +
            "  \"addressLine1\" : \"456 Some St\",\n" +
            "  \"addressLine2\" : \"Apt 2\",\n" +
            "  \"city\" : \"New York City\",\n" +
            "  \"state\" : \"NY\",\n" +
            "  \"zipcode\" : \"10012\"\n" +
            "}";

    //language=Json
    public static final String ADDRESS_3_STRING = "{\n" +
            "  \"addressLine1\" : \"789 Some St\",\n" +
            "  \"addressLine2\" : \"Apt 3\",\n" +
            "  \"city\" : \"Nashville\",\n" +
            "  \"state\" : \"TN\",\n" +
            "  \"zipcode\" : \"37024\"\n" +
            "}";
    public static final Address ADDRESS_OBJECT_1 = Address.builder()
            .addressLine1("123 Some St")
            .addressLine2("Apt 1")
            .city("Chicago")
            .state("IL")
            .zipcode("60676")
            .build();

    public static final Address ADDRESS_OBJECT_2 = Address.builder()
            .addressLine1("456 Some St")
            .addressLine2("Apt 2")
            .city("New York City")
            .state("NY")
            .zipcode("10012")
            .build();

    public static final Address ADDRESS_OBJECT_3 = Address.builder()
            .addressLine1("789 Some St")
            .addressLine2("Apt 3")
            .city("Nashville")
            .state("TN")
            .zipcode("37024")
            .build();
    public static final String ADDRESS_CITY_1 = "Chicago";

    public static final String ADDRESS_CITY_2 = "New York City";

    public static final String ADDRESS_CITY_3 = "Nashville";

    public static final String ADDRESS_ZIPCODE_1 = "60676";

    public static final String ADDRESS_ZIPCODE_2 = "10012";

    public static final String ADDRESS_ZIPCODE_3 = "37024";

    public static final String SOME_SSN_1 = "123-45-6789";

    public static final String SOME_SSN_2 = "213-54-6879";

    public static final String SOME_SSN_3 = "312-45-9687";

    public static final String SOME_PHONE_1 = "123-456-7890";

    public static final String SOME_PHONE_2 = "312-465-7089";

    public static final String SOME_PHONE_3 = "213-546-8790";

    public static final LocalDate SOME_DOB_1 = of(1995, 1, 1);

    public static final LocalDate SOME_DOB_2 = of(1994, 2, 3);

    public static final LocalDate SOME_DOB_3 = of(1998, 3, 4);

    public static final LocalDate SOME_DOB_AFTER_TODAY = now().plus(Period.ofDays(1));

    public static final LocalDate SOME_DOB_BELOW_18_YEARS = now().minusYears(17);

    public static final String SOME_DOB_STRING_1 = SOME_DOB_1.format(ISO_LOCAL_DATE);

    public static final String SOME_DOB_AFTER_TODAY_STRING = SOME_DOB_AFTER_TODAY.format(ISO_LOCAL_DATE);

    public static final String SOME_DOB_BELOW_18_YEARS_STRING = SOME_DOB_BELOW_18_YEARS.format(ISO_LOCAL_DATE);

    public static final String SOME_DOB_STRING_2 = SOME_DOB_2.format(ISO_LOCAL_DATE);

    public static final String SOME_DOB_STRING_3 = SOME_DOB_3.format(ISO_LOCAL_DATE);
    public static final String ENCYRPTED_SSN_1 = "someEncryptedSsn1";

    public static final String ENCYRPTED_SSN_2 = "someEncryptedSsn2";

    public static final String ENCYRPTED_SSN_3 = "someEncryptedSsn3";

    public static final String ENCRYPTED_PHONE_NUMBER_1 = "someEncryptedPhoneNumber1";

    public static final String ENCRYPTED_PHONE_NUMBER_2 = "someEncryptedPhoneNumber2";

    public static final String ENCRYPTED_PHONE_NUMBER_3 = "someEncryptedPhoneNumber3";

    public static final String ENCRYPTED_DOB_1 = "someEncryptedDob1";

    public static final String ENCRYPTED_DOB_2 = "someEncryptedDob2";

    public static final String ENCRYPTED_DOB_3 = "someEncryptedDob3";

    public static final String STATUS_MESSAGE_ATTRIBUTE = "statusMessage";

    public static final String ADDRESS_ATTRIBUTE = "address";

    public static final String BUSINESS_OWNER_ATTRIBUTE = "businessOwner";

    public static final String IS_UPDATE_ATTRIBUTE = "isUpdate";
    public static final String HOME_INDEX_STATUS_MESSAGE = "Hi Admin, Welcome!";

    public static final String HOME_PAGE = "index";

    public static final String CREATE_UPDATE_PAGE = "create-update-business-owner";

    public static final String CONTEXT_PATH_WEB = "/business-owner/service/web";

    public static final String SUCCESS_MESSAGE_ATTRIBUTE = "successMessage";

    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";

    public static final String DELETION_SUCCESS = "Deletion of business owner with id -> 1 successful. ";

    public static final String DELETION_FAILED = "Business owner with id -> 1 requested to delete does not exist. ";


    public static final String SUCCESS_PAGE = "success";

    public static final String ERROR_PAGE = "error";

    public static final String APP_EXCEPTION_MSG = "someAppException";

    public static final String GLOBAL_EXCEPTION_MSG = "someRuntimeException";

    public static final String MISSING_SERVLET_EXCEPTION_PARAMETER = "someParameter";
    public static final String MISSING_SERVLET_EXCEPTION_PARAMETER_TYPE = "someParameterType";

    public static final String MISSING_SERVLET_ERROR_ATTRIBUTE = "Required request parameter '"+
            MISSING_SERVLET_EXCEPTION_PARAMETER + "' for method parameter type " +
            MISSING_SERVLET_EXCEPTION_PARAMETER_TYPE + " is not present";

    public static final String INVALID_REFERENCE_EXCEPTION_MSG = "someInvalidRfrnceException";

}
