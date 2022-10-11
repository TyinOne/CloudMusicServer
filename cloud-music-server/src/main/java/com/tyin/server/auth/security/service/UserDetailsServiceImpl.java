package com.tyin.server.auth.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Maps;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.Asserts;
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
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.tyin.server.auth.security.constant.ConstantKey.LOGIN_USER_KEY;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser user = adminUserRepository.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, username));
        if (Objects.isNull(user)) return null;
        Asserts.isTrue(!user.getDisabled(), ResMessageConstants.USER_DISABLED);
        Set<String> roles = adminRoleRepository.selectRolesByUserId(user.getId());
        return createLoginUser(user, roles);
    }

    private AdminUserLoginRes createLoginUser(AdminUser user, Set<String> roles) {
        AdminUserLoginRes res = new AdminUserLoginRes(
                user.getId(),
                "",
                user.getNickName(),
                user.getAccount(),
                propertiesComponents.getOssUrl() + user.getAvatar(),
                roles,
                adminUserService.getPermissionByRole(roles),
                user.getDisabled());
        res.setPassword(user.getPassword());
        String uuid = StringUtils.getUuid();
        res.setKey(res.getAccount() + ":" + uuid);
        res.setToken(createToken(res));
        return res;
    }

    private String createToken(AdminUserLoginRes user) {
        String token = user.getKey();
        Map<String, Object> claims = Maps.newHashMap();
        claims.put(LOGIN_USER_KEY, token);
        return tokenService.createToken(claims, user.getAccount());
    }

    public AdminUserLoginRes login(AdminLoginValid adminLoginValid) {
        String md5Password = StringUtils.getMd5(StringUtils.getMd5(adminLoginValid.getPassword()));
        Authentication authentication = SpringUtils.getBean(AuthenticationManager.class).authenticate(new UsernamePasswordAuthenticationToken(adminLoginValid.getAccount(), md5Password));
        return (AdminUserLoginRes) authentication.getPrincipal();
    }
}
