package com.tyin.server.auth;

import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.server.service.IAdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.tyin.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.core.constants.PermissionConstants.PERMISSION_SERVICE;

/**
 * @author Tyin
 * @date 2022/4/7 9:54
 * @description ...
 */
@Component(PERMISSION_SERVICE)
@RequiredArgsConstructor
public class AdminPermissionService {
    private final IAdminUserService adminUserService;
    private AuthAdminUser authUser;

//    @Synchronized
    public AuthAdminUser getAuthAdminUser() {
        return authUser;
    }

    //    @Synchronized
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
            return true;
        }
        return permissions.contains(permission);
    }
}
