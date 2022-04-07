package com.tyin.cloud.core.filter;

import com.tyin.cloud.core.auth.PermissionService;
import com.tyin.cloud.core.auth.resolver.AuthUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.service.common.IUserCacheService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.tyin.cloud.core.constants.CommonConstants.ENV;
import static com.tyin.cloud.core.constants.CommonConstants.TOKEN;
import static com.tyin.cloud.core.utils.EnvUtils.getAuthUserClass;
import static com.tyin.cloud.core.utils.EnvUtils.getPrefix;

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
        String env = request.getHeader(ENV);
        Class<? extends AuthUser> authUserClass = getAuthUserClass(env);
        String prefix = getPrefix(env);
        AuthUser userCache = userCacheService.getUserCache(redisComponents.get(prefix + token), authUserClass);
        if (Objects.nonNull(userCache) && Objects.isNull(permissionService.getAuthUser())) {
            permissionService.setAuthUser(userCache);
        }
        filterChain.doFilter(request, response);
    }
}
