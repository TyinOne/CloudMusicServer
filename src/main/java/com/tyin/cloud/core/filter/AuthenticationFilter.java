package com.tyin.cloud.core.filter;

import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.auth.PermissionService;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.service.common.IUserCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.tyin.cloud.core.constants.CommonConstants.TOKEN;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/4/7 9:52
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final PermissionService permissionService;
    private final IUserCacheService userCacheService;
    private final RedisComponents redisComponents;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
