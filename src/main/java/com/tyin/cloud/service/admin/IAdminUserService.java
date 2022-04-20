package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.entity.AdminUserDetailRes;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.res.AdminUserPermissionRes;

/**
 * @author Tyin
 * @date 2022/3/31 13:45
 * @description ...
 */
public interface IAdminUserService {
    /**
     * 用户登录
     *
     * @param adminLoginParams 登录参数
     * @param ipAddress        登录IP
     * @return token
     */
    AdminUserLoginRes login(AdminLoginParams adminLoginParams, Long ipAddress);

    AdminUserPermissionRes getUserPermission(AuthAdminUser user);

    AdminUserDetailRes getUserInfo(AuthAdminUser user);
}
