package com.shanthan.springmvcdemo.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class Encryption {

    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "aesEncryptionKey".getBytes(StandardCharsets.UTF_8); // 16 characters

    public static String encrypt(String stringToEncrypt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] cipherText = cipher.doFinal(stringToEncrypt.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String stringToDecrypt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(stringToDecrypt));
        return new String(plainText);
    }
}
