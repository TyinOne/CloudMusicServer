package com.tyin.cloud.core.utils;

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

}
