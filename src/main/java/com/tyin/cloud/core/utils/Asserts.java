package com.tyin.cloud.core.utils;

import com.tyin.cloud.core.exception.ApiException;
import com.tyin.cloud.core.exception.handle.BaseErrorInfoInterface;

/**
 * @author Tyin
 * @date 2022/3/26 2:45
 * @description ...
 */
public class Asserts {
    public static void isTrue(boolean b, String message) {
        if (!b) {
            throw new ApiException(message);
        }
    }

    public static void isTrue(boolean b, BaseErrorInfoInterface errorCode) {
        if (!b) {
            throw new ApiException(errorCode);
        }
    }

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(BaseErrorInfoInterface errorCode) {
        throw new ApiException(errorCode);
    }

    public static void notEmpty(String string, String message) {
        if (StringUtils.isEmpty(string)) {
            fail(message);
        }
    }

    public static void notEmpty(String string, BaseErrorInfoInterface errorCode) {
        if (StringUtils.isEmpty(string)) {
            fail(errorCode);
        }
    }

    public static void checkTrue(Boolean b, String message) {
        if (b) {
            fail(message);
        }
    }

    public static void checkFalse(Boolean b, String message) {
        if (!b) {
            fail(message);
        }
    }

    public static void isTrue(boolean hasPermission) {
    }
}
