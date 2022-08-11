package com.tyin.core.utils;

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

    public static void main(String[] args) {
        System.out.println("88a0f943de6faf4be5d5a144c32b3932e6bc7c4b5ce060def64abb20539f9d00".length());
    }
}
