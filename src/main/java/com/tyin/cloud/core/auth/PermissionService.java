package com.tyin.cloud.core.auth;

import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PermissionService {
    private final IAdminUserService adminUserService;
    private AuthAdminUser authUser;

    @Synchronized
    public AuthAdminUser getAuthAdminUser() {
        return authUser;
    }

    @Synchronized
    public void setAuthAdminUser(AuthAdminUser authUser) {
        this.authUser = authUser;
    }

    /**
     * 接口鉴权
     *
     * @param permission 接口权限字符
     * @return boolean
     */
    public Boolean hasPermission(String permission) {
        Set<String> permissions = adminUserService.getPermissionByRole(authUser.getRoleId(), authUser.getRole());
        if (permissions.contains(ADMIN_SECURITY)) {
            authUser = null;
            return true;
        }
        authUser = null;
        return permissions.contains(permission);
    }
}
