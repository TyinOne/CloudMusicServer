package com.tyin.cloud.core.utils;

import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.auth.AuthClientUser;
import com.tyin.cloud.core.auth.resolver.AuthUser;

import static com.tyin.cloud.core.constants.CommonConstants.ADMIN;
import static com.tyin.cloud.core.constants.CommonConstants.CLIENT;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;
import static com.tyin.cloud.core.constants.RedisKeyConstants.CLIENT_USER_TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/4/7 10:06
 * @description ...
 */
public class EnvUtils {

    public static String getPrefix(String env) {
        return switch (env) {
            case CLIENT -> CLIENT_USER_TOKEN_PREFIX;
            case ADMIN -> ADMIN_USER_TOKEN_PREFIX;
            default -> "";
        };
    }

    public static Class<? extends AuthUser> getAuthUserClass(String env) {
        return switch (env) {
            case CLIENT -> AuthClientUser.class;
            case ADMIN -> AuthAdminUser.class;
            default -> null;
        };
    }
}
