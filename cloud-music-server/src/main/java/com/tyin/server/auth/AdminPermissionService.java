package com.tyin.server.auth;

import com.tyin.core.auth.admin.TokenService;
import com.tyin.core.auth.admin.utils.SecurityUtils;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.service.IAdminUserService;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.tyin.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.core.constants.PermissionConstants.PERMISSION_SERVICE;
import static com.tyin.core.constants.SecurityConstantKey.*;

/**
 * @author Tyin
 * @date 2022/4/7 9:54
 * @description ...
 */
@Component(PERMISSION_SERVICE)
@RequiredArgsConstructor
public class AdminPermissionService {
    private final IAdminUserService adminUserService;
    private final TokenService tokenService;
    private final RedisComponents redisComponents;

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

    public AdminUserLoginRes getLoginAdminUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = tokenService.parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(LOGIN_USER_KEY);
                String userKey = tokenService.getTokenKey(uuid);
                String cache = redisComponents.get(userKey);
                Asserts.isTrue(StringUtils.isNotEmpty(cache), ResMessageConstants.AUTH_FILE);
                return JsonUtils.toJavaObject(cache, AdminUserLoginRes.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        return token;
    }
}
