package com.tyin.server.auth.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.SpringUtils;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.params.valid.AdminLoginValid;
import com.tyin.server.repository.AdminRoleRepository;
import com.tyin.server.repository.AdminUserRepository;
import com.tyin.server.service.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.tyin.server.auth.security.constant.ConstantKey.LOGIN_TOKEN_KEY;
import static com.tyin.server.auth.security.constant.ConstantKey.LOGIN_USER_KEY;
import static com.tyin.server.auth.security.utils.LoginUtils.getColumns;

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
    private final PropertiesComponents propertiesComponents;
    private final TokenService tokenService;
    private final RedisComponents redisComponents;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user = adminUserRepository.selectOne(Wrappers.<AdminUser>query().eq(getColumns(username), username).lambda());
        if (Objects.isNull(user)) return null;
        if (StringUtils.isNotEmpty(user.getToken())) {
            String s = redisComponents.get(LOGIN_TOKEN_KEY + user.getToken());
            if (StringUtils.isNotEmpty(s)) {
                AdminUserLoginRes res = JsonUtils.toJavaObject(s, AdminUserLoginRes.class);
                if (Objects.nonNull(res) && Objects.nonNull(res.getExpireTime()) && res.getExpireTime() > System.currentTimeMillis()) {
                    res.setPassword(user.getPassword());
                    return tokenService.verifyToken(res);
                }
            }
        }
        Asserts.isTrue(!user.getDisabled(), ResMessageConstants.USER_DISABLED);
        Set<String> roles = adminRoleRepository.selectRolesByUserId(user.getId());
        return createLoginUser(user, roles);
    }

    private AdminUserLoginRes createLoginUser(AdminUser user, Set<String> roles) {
        String uuid = StringUtils.getUuid();
        String uuidKey = user.getAccount() + ":" + uuid;
        AdminUserLoginRes res = new AdminUserLoginRes(
                user.getId(),
                "",
                uuidKey,
                user.getNickName(),
                user.getAccount(),
                user.getAvatar(),
                roles,
                adminUserService.getPermissionByRole(roles),
                user.getDisabled());
        res.setPassword(user.getPassword());
        res.setToken(createToken(res));
        return res;
    }

    private String createToken(AdminUserLoginRes user) {
        String token = user.getUuid();
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(LOGIN_USER_KEY, token);
        return tokenService.createToken(claims, user.getAccount());
    }

    public AdminUserLoginRes login(AdminLoginValid adminLoginValid) {
        String md5Password = StringUtils.getMd5(StringUtils.getMd5(adminLoginValid.getPassword()));
        Authentication authentication = SpringUtils.getBean(AuthenticationManager.class).authenticate(new UsernamePasswordAuthenticationToken(adminLoginValid.getAccount(), md5Password));
        return (AdminUserLoginRes) authentication.getPrincipal();
    }

    public String getPassword(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
