package com.tyin.cloud.model.res;

import com.tyin.cloud.core.auth.AuthAdminUser;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/31 13:47
 * @description ...
 */
@NoArgsConstructor
public class AdminUserLoginRes extends AuthAdminUser {

    public AdminUserLoginRes(String token, String nickName, String account, String avatar, String role, Set<String> permissions) {
        super(token, nickName, account, avatar, role, permissions);
    }
}
