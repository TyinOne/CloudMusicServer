package com.tyin.server.auth.security.constant;

/**
 * @author Tyin
 * @date 2022/9/29 17:55
 * @description ...
 */
public class ConstantKey {
    public final static String SIGNING_KEY = "tyinzero060013!@#$%";

    public final static String HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public final static Long EXPIRE_TIME = 360L;
    public final static String LOGIN_USER_KEY = "login_user_key";

    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 每秒的毫秒
     */
    public static final Long MILLIS_SECOND = 1000L;

    public static final Long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    public static final Long MILLIS_MINUTE_TEN = 20 * MILLIS_MINUTE;
}
