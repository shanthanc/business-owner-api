package com.shanthan.businessowner.util;

import java.nio.charset.StandardCharsets;

public class BusinessOwnerConstants {

    private BusinessOwnerConstants() {
        //no instantiation
    }

    public static final byte[] KEY = "aesEncryptionKey".getBytes(StandardCharsets.UTF_8); // 16 characters

    public static final String STATUS_MESSAGE_ATTRIBUTE = "statusMessage";

    public static final String SUCCESS_MESSAGE_ATTRIBUTE = "successMessage";

    public static final String ADDRESS_ATTRIBUTE = "address";

    public static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    public static final String BUSINESS_ID_ATTRIBUTE = "businessId";

    public static final String BUSINESS_OWNER_ATTRIBUTE = "businessOwner";

    public static final String IS_UPDATE_ATTRIBUTE = "isUpdate";
    public static final String HOME_INDEX_STATUS_MESSAGE = "Hi Admin, Welcome!";

    public static final String HOME_PAGE = "index";

    public static final String CREATE_UPDATE_PAGE = "create-update-business-owner";

    public static final String SUCCESS_PAGE = "success";

    public static final String ERROR_PAGE = "error";


    public static final String BUSINESS_OWNER_CREATION_SUCCESS_1 = "Business Owner with id -> ";
    public static final String BUSINESS_OWNER_CREATION_SUCCESS_2 = " created successfully! ";

    public static final String DEFAULT_ERROR_MSG = "Sorry, we're currently facing technical difficulties.\n" +
            "        Our engineers and other staff are currently working to resolve the error soon.\n" +
            "        Thank you for your patience.";

    public static final String BUSINESS_OWNER_UPDATED_SUCCESS_2 = " updated successfully! ";

}
