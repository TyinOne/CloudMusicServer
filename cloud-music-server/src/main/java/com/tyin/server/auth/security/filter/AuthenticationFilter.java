package com.tyin.server.auth.security.filter;

import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.auth.security.utils.SecurityUtils;
import com.tyin.server.auth.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.tyin.server.auth.security.constant.ConstantKey.TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/9/29 17:00
 * @description ...
 */
@Slf4j
@Component
public class AuthenticationFilter extends BasicAuthenticationFilter {

    private final TokenService tokenService;

    public AuthenticationFilter(TokenService tokenService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        AdminUserLoginRes user = tokenService.getLoginAdminUser(request);

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

    private UsernamePasswordAuthenticationToken getAuthentication(AdminUserLoginRes userLoginRes) throws ServletException, IOException {
        return new UsernamePasswordAuthenticationToken(userLoginRes, null, userLoginRes.getAuthorities());
    }
}
