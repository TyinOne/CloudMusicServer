package com.tyin.core.auth.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.tyin.core.auth.admin.utils.LoginUtils;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.exception.ApiException;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.module.valid.AdminLoginValid;
import com.tyin.core.repository.admin.AdminRoleRepository;
import com.tyin.core.repository.admin.AdminUserRepository;
import com.tyin.core.service.IAdminUserService;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.tyin.core.constants.SecurityConstantKey.*;

/**
 * @author Tyin
 * @date 2022/9/29 17:12
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;
    private final AdminRoleRepository adminRoleRepository;
    private final IAdminUserService adminUserService;
    private final TokenService tokenService;
    private final RedisComponents redisComponents;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user = adminUserRepository.selectOne(Wrappers.<AdminUser>query().eq(LoginUtils.getColumns(username), username).lambda());
        if (Objects.isNull(user)) return null;
        Set<String> roles = adminRoleRepository.selectRolesByUserId(user.getId());
        AdminUserLoginRes res = createLoginUser(user, roles);
        if (StringUtils.isNotEmpty(user.getToken())) {
            String s = redisComponents.get(LOGIN_TOKEN_KEY + user.getToken());
            if (StringUtils.isNotEmpty(s)) {
                AdminUserLoginRes resOld = JsonUtils.toJavaObject(s, AdminUserLoginRes.class);
                if (Objects.nonNull(resOld) && Objects.nonNull(resOld.getExpireTime()) && resOld.getExpireTime() > System.currentTimeMillis()) {
                    res.setLoginTime(System.currentTimeMillis());
                    res.setExpireTime(res.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
                    res.setRedisKey(user.getToken());
                    res.setPassword(user.getPassword());
                    res.setToken(createToken(res));
                    return res;
                }
            }
        }
        String uuid = StringUtils.getUuid();
        String redisKey = user.getAccount() + ":" + uuid;
        res.setRedisKey(redisKey);
        res.setLoginTime(System.currentTimeMillis());
        res.setExpireTime(res.getLoginTime() + EXPIRE_TIME * MILLIS_MINUTE);
        Asserts.isTrue(!user.getDisabled(), ResMessageConstants.USER_DISABLED);
        res.setPassword(user.getPassword());
        res.setToken(createToken(res));
        return res;
    }

    private AdminUserLoginRes createLoginUser(AdminUser user, Set<String> roles) {
        AdminUserLoginRes res = new AdminUserLoginRes(
                user.getId(),
                "",
                "",
                user.getNickName(),
                user.getAccount(),
                user.getAvatar(),
                roles,
                adminUserService.getPermissionByRole(roles),
                user.getDisabled());
        res.setPassword(user.getPassword());
        return res;
    }

    private String createToken(AdminUserLoginRes user) {
        String token = user.getRedisKey();
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(LOGIN_USER_KEY, token);
        return tokenService.createToken(claims, user.getAccount());
    }

    public AdminUserLoginRes login(AdminLoginValid adminLoginValid) {
        Authentication authentication;
        AdminUserLoginRes res;
        try {
            authentication = authenticationConfiguration.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(adminLoginValid.getAccount(), adminLoginValid.getPassword()));
            res = (AdminUserLoginRes) authentication.getPrincipal();
            redisComponents.save(LOGIN_TOKEN_KEY + res.getRedisKey(), JsonUtils.toJSONString(res), res.getExpireTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(e.getMessage());
        }
        return res;
    }

    public String getPassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
