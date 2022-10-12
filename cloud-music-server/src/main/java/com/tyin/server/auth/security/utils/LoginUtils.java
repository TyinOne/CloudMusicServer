package com.tyin.server.auth.security.utils;

import static com.tyin.core.constants.ParamsConstants.*;
import static com.tyin.core.constants.PatternConstants.MAIL_PATTERN;
import static com.tyin.core.constants.PatternConstants.TEL_PATTERN;

/**
 * @author Tyin
 * @date 2022/10/12 14:18
 * @description ...
 */
public class LoginUtils {
    public static String getColumns(String username) {
        if (username.matches(TEL_PATTERN)) return PHONE;
        if (username.matches(MAIL_PATTERN)) return MAIL;
        return ACCOUNT;
    }
}
