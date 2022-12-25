package com.tyin.server.auth.security.filter;

import com.tyin.core.auth.admin.TokenService;
import com.tyin.core.auth.admin.utils.SecurityUtils;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.auth.AdminPermissionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.tyin.core.constants.SecurityConstantKey.TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/9/29 17:00
 * @description ...
 */
@Slf4j
@Component
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private final TokenService tokenService;
    private final AdminPermissionService adminPermissionService;

    public AuthenticationFilter(TokenService tokenService, AdminPermissionService adminPermissionService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.adminPermissionService = adminPermissionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        AdminUserLoginRes user = adminPermissionService.getLoginAdminUser(request);

        String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header) || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        if (Objects.nonNull(user) && Objects.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(user);
            UsernamePasswordAuthenticationToken authentication = getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(AdminUserLoginRes userLoginRes) {
        return new UsernamePasswordAuthenticationToken(userLoginRes, null, userLoginRes.getAuthorities());
    }
}
