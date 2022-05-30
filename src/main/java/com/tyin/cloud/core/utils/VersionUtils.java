package com.tyin.cloud.core.utils;

/**
 * @author Tyin
 * @date 2022/5/23 16:49
 * @description ...
 */
public class VersionUtils {

    public static String longToString(long versionLong) {
        String[] ipArr = new String[3];
        for (int i = 0; i < ipArr.length; i++) {
            long n = (versionLong >>> (8 * i)) & (0xFF);
            ipArr[2 - i] = String.valueOf(n);
        }
        return String.join(".", ipArr);
    }
    public static long stringToInt(String v) {
        String[] version = v.split("\\.");
        return (Long.parseLong(version[0]) << 16) + (Long.parseLong(version[1]) << 8) + Integer.parseInt(version[2]);
    }
}
