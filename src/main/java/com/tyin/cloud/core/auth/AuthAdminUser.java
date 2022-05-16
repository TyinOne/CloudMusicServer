package com.tyin.cloud.core.auth;

import com.tyin.cloud.core.auth.resolver.AuthUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/26 3:16
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthAdminUser implements AuthUser {
    private String token;
    private String nickName;
    private String account;
    private String avatar;
    private String role;
    private Set<String> permissions;
}
