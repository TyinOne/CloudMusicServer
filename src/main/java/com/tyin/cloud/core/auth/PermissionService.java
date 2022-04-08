package com.tyin.cloud.core.auth;

import com.tyin.cloud.core.auth.resolver.AuthUser;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/7 9:54
 * @description ...
 */
@Component("permission")
public class PermissionService {
    private AuthUser authUser;

    @Synchronized
    public AuthUser getAuthUser() {
        return authUser;
    }

    @Synchronized
    public void setAuthUser(AuthUser authUser) {
        this.authUser = authUser;
    }

    public Boolean hasPermission(String permission) {
        Set<String> permissions = authUser.getPermissions();
        if (permissions.contains("*:*:*")) return true;
        return permissions.contains(permission);
    }
}