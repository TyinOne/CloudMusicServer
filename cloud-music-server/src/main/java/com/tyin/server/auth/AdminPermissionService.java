package com.tyin.server.auth;

import com.tyin.server.auth.security.utils.SecurityUtils;
import com.tyin.server.service.IAdminUserService;
import lombok.RequiredArgsConstructor;
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

    /**
     * 接口鉴权
     *
     * @param permission 接口权限字符
     * @return boolean
     */
    public Boolean hasPermission(String permission) {
        Set<String> permissions = adminUserService.getPermissionByRole(SecurityUtils.getLoginUser().getRoles());
        if (permissions.contains(ADMIN_SECURITY)) {
            return true;
        }
        return permissions.contains(permission);
    }
}
