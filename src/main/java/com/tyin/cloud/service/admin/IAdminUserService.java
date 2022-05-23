package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.entity.AdminUserDetailRes;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminAccountDetailRes;
import com.tyin.cloud.model.res.AdminAccountRes;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.valid.SaveAccountValid;

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
