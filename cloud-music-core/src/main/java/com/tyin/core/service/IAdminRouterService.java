package com.tyin.core.service;


import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminRouterListRes;

/**
 * @author Tyin
 * @date 2022/5/5 16:28
 * @description ...
 */
public interface IAdminRouterService {
    AdminRouterListRes getRouterByPermission(AuthAdminUser user);
}
