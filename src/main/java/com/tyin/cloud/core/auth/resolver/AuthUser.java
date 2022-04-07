package com.tyin.cloud.core.auth.resolver;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/29 23:20
 * @description ...
 */
public interface AuthUser {
    default Set<String> getPermissions() {
        return null;
    }
}
