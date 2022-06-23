package com.tyin.server.service;

import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.params.valid.AdminLoginParams;
import com.tyin.server.params.valid.SaveAccountValid;

import java.util.Set;

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

    AdminUserDetailRes getUserInfo(AuthAdminUser user);

    AdminUser getUserEntity(String account);

    PageResult<AdminAccountRes, ?> getUserList(Long size, Long current, String name, Long roleId, Long disabled);

    AdminAccountDetailRes getAccountDetail(String account);

    void saveAccountInfo(SaveAccountValid valid);

    AdminUserLoginRes getUserSession(AuthAdminUser user);

    Set<String> getPermissionByRole(Long roleId, String roleValue);

    void logout(AuthAdminUser user);
}
