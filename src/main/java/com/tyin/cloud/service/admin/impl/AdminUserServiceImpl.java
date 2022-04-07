package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.res.AdminUserPermissionRes;
import com.tyin.cloud.repository.admin.AdminUserRepository;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRoleService;
import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.tyin.cloud.core.constants.ParamsConstants.*;
import static com.tyin.cloud.core.constants.PatternConstants.MAIL_PATTERN;
import static com.tyin.cloud.core.constants.PatternConstants.TEL_PATTERN;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;
import static com.tyin.cloud.core.constants.ResMessageConstants.AUTH_FAILED;
import static com.tyin.cloud.core.constants.ResMessageConstants.USER_DISABLED;

/**
 * @author Tyin
 * @date 2022/3/31 13:45
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements IAdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final RedisComponents redisComponents;
    private final PropertiesComponents propertiesComponents;
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;

    @Override
    public AdminUserLoginRes login(AdminLoginParams adminLoginParams, Integer ipAddress) {
        String username = adminLoginParams.getUsername();
        String password = adminLoginParams.getPassword();
        password = StringUtils.sha256Encode(DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8))).toUpperCase(Locale.ROOT);
        AdminUser adminUser = adminUserRepository.selectOne(Wrappers.<AdminUser>query().eq(getColumns(username), username).lambda().eq(AdminUser::getPassword, password));
        Asserts.isTrue(Objects.nonNull(adminUser), AUTH_FAILED);
        Asserts.isTrue(!adminUser.getDisabled(), USER_DISABLED);
        adminUser.setAvatar(StringUtils.isNotEmpty(adminUser.getAvatar()) ? propertiesComponents.getOssUrl() + adminUser.getAvatar() : StringUtils.EMPTY);
        String token;
        if (StringUtils.isNotEmpty(adminUser.getToken())) {
            token = adminUser.getToken();
        } else {
            token = StringUtils.getUuid();
            adminUser.setToken(token);
            adminUserRepository.updateById(adminUser);
        }
        List<AdminRole> roles = adminRoleService.getRoles(adminUser);
        Set<String> roleValues = roles.stream().map(AdminRole::getValue).collect(Collectors.toSet());
        HashSet<String> permissions = roleValues.contains("admin") ? Sets.newHashSet("*:*:*") : adminMenuService.getMenuPermission(adminUser);
        AuthAdminUser user = AuthAdminUser.builder().token(token).name(adminUser.getName()).account(adminUser.getAccount()).avatar(adminUser.getAvatar()).roles(roleValues).permissions(permissions).build();
        redisComponents.save(ADMIN_USER_TOKEN_PREFIX + token, JsonUtils.toJSONString(user));
        return AdminUserLoginRes.builder().token(token)
                .name(adminUser.getName())
                .avatar(adminUser.getAvatar())
                .roles(roleValues)
                .btn(Lists.newArrayList())
                .build();
    }

    @Override
    public AdminUserPermissionRes getUserPermission(AuthAdminUser user) {
        return AdminUserPermissionRes.builder()
                .routerRes(adminMenuService.getRouterForUser(adminUserRepository.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, user.getAccount())).getId()))
                .build();
    }

    private String getColumns(String username) {
        if (username.matches(TEL_PATTERN)) return PHONE;
        if (username.matches(MAIL_PATTERN)) return MAIL;
        return ACCOUNT;
    }
}
