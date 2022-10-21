package com.tyin.server.auth.security;

import com.tyin.core.components.RedisComponents;
import com.tyin.core.exception.ApiException;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.JsonUtils;
import com.tyin.server.auth.security.service.TokenService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.tyin.core.constants.ResMessageConstants.AUTH_FAILED;
import static com.tyin.server.auth.security.constant.ConstantKey.EXPIRE_TIME;
import static com.tyin.server.auth.security.constant.ConstantKey.MILLIS_MINUTE;

/**
 * @author Tyin
 * @date 2022/9/28 18:07
 * @description ...
 */
public class CloudAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisComponents redisComponents;
    private final TokenService tokenService;

    public CloudAuthenticationProvider(TokenService tokenService, UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, RedisComponents redisComponents) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.redisComponents = redisComponents;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(name);
        if (Objects.nonNull(userDetails) && userDetails instanceof AdminUserLoginRes) {
            if (bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
                // 这里设置权限和角色
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                AdminUserLoginRes res = (AdminUserLoginRes) userDetails;
                String key = res.getUuid();
                //存储redis
                res.setLoginTime(System.currentTimeMillis());
                res.setExpireTime(res.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
                res.setUuid(key);
                redisComponents.save(tokenService.getTokenKey(key), JsonUtils.toJSONString(res), EXPIRE_TIME * MILLIS_MINUTE, TimeUnit.MINUTES);
                return new UsernamePasswordAuthenticationToken(res, password, authorities);
            }
        }
        throw new ApiException(AUTH_FAILED);
    }

    /**
     * 是否可以提供输入类型的认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
