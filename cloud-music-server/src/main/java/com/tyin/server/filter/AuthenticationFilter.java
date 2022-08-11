package com.tyin.server.filter;

import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.auth.AdminPermissionService;
import com.tyin.server.service.IUserCacheService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.tyin.core.constants.CommonConstants.TOKEN;
import static com.tyin.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/4/7 9:52
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final AdminPermissionService permissionService;
    private final IUserCacheService userCacheService;
    private final RedisComponents redisComponents;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(TOKEN);
        if (StringUtils.isNotEmpty(token)) {
            AuthAdminUser userCache = userCacheService.getUserCache(redisComponents.get(ADMIN_USER_TOKEN_PREFIX + token), AuthAdminUser.class);
            if (Objects.nonNull(userCache) && Objects.isNull(permissionService.getAuthAdminUser())) {
                permissionService.setAuthAdminUser(userCache);
            }
        }
        filterChain.doFilter(request, response);
    }
}
