package com.tyin.core.module.res.admin;

import com.tyin.core.module.bean.AuthAdminUser;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/31 13:47
 * @description ...
 */
@NoArgsConstructor
public class AdminUserLoginRes extends AuthAdminUser {
    public AdminUserLoginRes(Long id, String token, String nickName, String account, String avatar, Long roleId, String role, Set<String> permissions) {
        super(id, token, nickName, account, avatar, roleId, role, permissions);
    }
}
