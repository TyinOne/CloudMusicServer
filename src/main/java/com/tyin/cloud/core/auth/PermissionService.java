package com.tyin.cloud.core.auth;

import com.tyin.cloud.core.auth.resolver.AuthUser;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.tyin.cloud.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.cloud.core.constants.PermissionConstants.PERMISSION_SERVICE;

/**
 * @author Tyin
 * @date 2022/4/7 9:54
 * @description ...
 */
@Component(PERMISSION_SERVICE)
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

    /**
     * 接口鉴权
     *
     * @param permission 接口权限字符
     * @return boolean
     */
    public Boolean hasPermission(String permission) {
        Set<String> permissions = authUser.getPermissions();
        if (permissions.contains(ADMIN_SECURITY)) return true;
        return permissions.contains(permission);
    }
}
