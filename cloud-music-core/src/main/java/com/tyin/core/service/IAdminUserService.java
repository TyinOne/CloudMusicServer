package com.tyin.core.service;

import com.tyin.core.api.PageResult;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.module.valid.AdminRegisterValid;
import com.tyin.core.module.valid.SaveAccountValid;
import com.tyin.core.module.valid.UpdatePasswordValid;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/31 13:45
 * @description ...
 */
public interface IAdminUserService {

    /**
     * 用户登出
     *
     * @param user 当前登录用户
     */
    void logout(AuthAdminUser user);

    /**
     * 用户注册
     *
     * @param adminRegisterValid valid
     */
    void register(AdminRegisterValid adminRegisterValid);

    /**
     * 获取用户详情 返回信息
     *
     * @param user 当前登录用户
     * @return 用户详情Res
     */
    AdminUserDetailRes getUserInfo(AuthAdminUser user);

    /**
     * 用户名获取用户Entity
     *
     * @param account 用户名
     * @return 用户Entity
     */
    AdminUser getUserEntity(String account);

    /**
     * 获取用户列表
     *
     * @param size     页长度
     * @param current  当前页
     * @param name     用户名字
     * @param roleId   角色Id
     * @param disabled 是否禁用
     * @return AdminAccountRes
     */
    PageResult<AdminAccountRes, ?> getUserList(Long size, Long current, String name, Long roleId, Long disabled);

    /**
     * 获取用户详情
     *
     * @param account 用户名
     * @return 用户管理
     */
    AdminAccountDetailRes getAccountDetail(String account);

    /**
     * 保存用户修改信息
     *
     * @param saveAccountValid valid
     */
    void saveAccountInfo(SaveAccountValid saveAccountValid);

    /**
     * 用户缓存获取
     *
     * @param user 当前登录用户
     * @return cache
     */
    AdminUserLoginRes getUserSession(AuthAdminUser user);

    /**
     * 用户权限
     *
     * @param roles 角色权限标识
     * @return Set 权限字符集
     */
    Set<String> getPermissionByRole(Set<String> roles);

    /**
     * 生成用户注册邀请码
     *
     * @param id   角色Id
     * @param user 当前登录用户
     * @return 邀请码Bean
     */
    InviteCodeBean generateInviteCode(Long id, AuthAdminUser user);

    /**
     * 更新token
     *
     * @param account   用户名
     * @param key       key
     * @param ipAddress
     */
    void updateToken(String account, String key, Long ipAddress);

    /**
     * 重置密码为cloudadmin
     *
     * @param valid valid
     */
    void resetPassword(UpdatePasswordValid valid);
}