package com.tyin.cloud.core.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Tyin
 * @date 2022/3/26 2:43
 * @description ...
 */
public class StringUtils extends org.springframework.util.StringUtils {
    public static String EMPTY = "";

    public static boolean isEmpty(CharSequence value) {
        return !isNotEmpty(value);
    }

    public static boolean isNotEmpty(CharSequence value) {
        return hasText(value);
    }

    public static boolean isBlank(CharSequence value) {
        return !isNotBlank(value);
    }

    public static boolean isNotBlank(CharSequence value) {
        return hasLength(value.toString().trim());
    }

    public static String sha256Encode(String value) {
        BigInteger sha = null;
        byte[] bytes = value.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes);
            sha = new BigInteger(messageDigest.digest());
            return sha.toString(32);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "").toUpperCase(Locale.ROOT);
        return uuid;
    }
}
