package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminRouterListRes;

/**
 * @author Tyin
 * @date 2022/5/5 16:28
 * @description ...
 */
public interface IAdminRouterService {
    AdminRouterListRes getRouterByPermission(AuthAdminUser user);
}
