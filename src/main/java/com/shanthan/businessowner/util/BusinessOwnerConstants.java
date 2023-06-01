package com.shanthan.businessowner.util;

import java.nio.charset.StandardCharsets;

public class BusinessOwnerConstants {

    private BusinessOwnerConstants() {
        //no instantiation
    }

    public static final byte[] KEY = "aesEncryptionKey".getBytes(StandardCharsets.UTF_8); // 16 characters
}
