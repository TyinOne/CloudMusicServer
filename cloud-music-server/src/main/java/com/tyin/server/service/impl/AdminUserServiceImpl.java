package com.tyin.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.entity.AdminUserExtra;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.*;
import com.tyin.core.utils.IpUtils;
import com.tyin.server.api.PageResult;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.params.valid.AdminLoginParams;
import com.tyin.server.params.valid.SaveAccountValid;
import com.tyin.server.repository.AdminUserExtraRepository;
import com.tyin.server.repository.AdminUserRepository;
import com.tyin.server.service.IAdminMenuService;
import com.tyin.server.service.IAdminRoleService;
import com.tyin.server.service.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.tyin.core.constants.ParamsConstants.*;
import static com.tyin.core.constants.PatternConstants.MAIL_PATTERN;
import static com.tyin.core.constants.PatternConstants.TEL_PATTERN;
import static com.tyin.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.core.constants.PermissionConstants.SUPPER_ROLE;
import static com.tyin.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;
import static com.tyin.core.constants.RedisKeyConstants.ROLE_BUTTON_PREFIX;
import static com.tyin.core.constants.ResMessageConstants.AUTH_FAILED;
import static com.tyin.core.constants.ResMessageConstants.USER_DISABLED;

/**
 * @author Tyin
 * @date 2022/3/31 13:45
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements IAdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserExtraRepository adminUserExtraRepository;
    private final RedisComponents redisComponents;
    private final PropertiesComponents propertiesComponents;
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;

    @Override
    public AdminUserLoginRes login(AdminLoginParams adminLoginParams, Long ipAddress) {
        String username = adminLoginParams.getAccount();
        String password = adminLoginParams.getPassword();
        password = StringUtils.sha256Encode(DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8))).toUpperCase(Locale.ROOT);
        AdminUser adminUser = adminUserRepository.selectOne(Wrappers.<AdminUser>query().eq(getColumns(username), username).lambda().eq(AdminUser::getPassword, password));
        Asserts.isTrue(Objects.nonNull(adminUser), AUTH_FAILED);
        Asserts.isTrue(!adminUser.getDisabled(), USER_DISABLED);
        String avatar = StringUtils.isNotEmpty(adminUser.getAvatar()) ? propertiesComponents.getOssUrl() + adminUser.getAvatar() : StringUtils.EMPTY;
        String token;
        if (StringUtils.isNotEmpty(adminUser.getToken())) {
            token = adminUser.getToken();
        } else {
            token = StringUtils.getUuid();
            adminUser.setToken(token);
        }
        adminUser.setLastLogin(ipAddress);
        adminUser.setLastLoginTime(DateUtils.getNowDate());
        adminUserRepository.updateById(adminUser);
        AdminRole role = adminRoleService.getRoles(adminUser.getId());
        Set<String> permissions = getPermissionByRole(role.getId(), role.getValue());
        AuthAdminUser user = AuthAdminUser.builder()
                .id(adminUser.getId())
                .roleId(role.getId())
                .token(token).nickName(adminUser.getNickName()).account(adminUser.getAccount()).avatar(avatar).permissions(permissions).role(role.getValue())
                .build();
        redisComponents.saveAsync(ADMIN_USER_TOKEN_PREFIX + token, JsonUtils.toJSONString(user));
        return new AdminUserLoginRes(
                adminUser.getId(),
                token,
                adminUser.getNickName(),
                user.getAccount(),
                avatar,
                role.getId(),
                role.getValue(),
                permissions
        );
    }

    @Override
    public AdminUserDetailRes getUserInfo(AuthAdminUser user) {
        String account = user.getAccount();
        String ossUrl = propertiesComponents.getOssUrl();
        return adminUserRepository.selectUserDetail(account, ossUrl);
    }

    @Override
    public AdminUser getUserEntity(String account) {
        return adminUserRepository.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, account));
    }

    @Override
    public PageResult<AdminAccountRes, ?> getUserList(Long size, Long current, String name, Long roleId, Long disabled) {
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .apply(disabled != -1, " `user`.`disabled` = {0} ", disabled)
                .apply(roleId != 0, " `role`.`id` = {0}", roleId)
                .and(StringUtils.isNotEmpty(name), i -> i
                        .apply(" INSTR(`user`.`nick_name`, {0}) > 0", name)
                        .or()
                        .apply(" INSTR(`extra`.`id_card_name`, {0}) > 0", name))
        ;
        IPage<AdminAccountRes> resPage = adminUserRepository.selectUserList(new Page<>(current, size), wrapper);
        return PageResult.buildResult(resPage);
    }

    @Override
    public AdminAccountDetailRes getAccountDetail(String account) {
        AdminAccountDetailRes res = adminUserRepository.selectAccountDetail(account);
        res.setAvatar(propertiesComponents.getOssUrl() + res.getAvatar());
        res.setLastLogin(IpUtils.longToIp(Long.parseLong(res.getLastLogin())));
        AdminRole roles = adminRoleService.getRoles(res.getId());
        if (Objects.nonNull(roles)) res.setRoleId(roles.getId() + "");
        return res;
    }

    @Override
    public void saveAccountInfo(SaveAccountValid valid) {
        //更新数据库
        AdminUser userBase = getUserEntity(valid.getAccount());
        AdminUser user = AdminUser.builder()
                .nickName(valid.getNickName())
                .mail(valid.getMail())
                .phone(valid.getPhone())
                .build();
        String avatar = userBase.getAvatar();
        if (StringUtils.isNotEmpty(valid.getAvatar().getFileName())) {
            SaveAccountValid.AvatarUpdate avatarUpdate = valid.getAvatar();
            String avatarUri = propertiesComponents.getOssImages() + userBase.getAccount() + "/avatar/";
            String tmpPath = propertiesComponents.getOssServer() + propertiesComponents.getOssTmp() + avatarUpdate.getFileName();
            String pathDir = propertiesComponents.getOssServer() + avatarUri;
            File source = new File(tmpPath);
            File target = new File(pathDir);
            try {
                FileUtils.copyFileToDirectory(source, target);
                avatar = avatarUri + avatarUpdate.getFileName();
                user.setAvatar(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //复制文件
        }
        adminRoleService.updateUserRole(valid.getAccount(), valid.getRoleId());
        adminUserRepository.update(user, Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, valid.getAccount()));
        AdminUserExtra userExtra = AdminUserExtra.builder()
                .birth(valid.getBirth())
                .region(valid.getRegion())
                .build();
        adminUserExtraRepository.update(userExtra, Wrappers.<AdminUserExtra>lambdaQuery().eq(AdminUserExtra::getUserId, userBase.getId()));
        //更新登录缓存
        if (StringUtils.isNotEmpty(userBase.getToken())) {
            if (redisComponents.existsKey(ADMIN_USER_TOKEN_PREFIX + userBase.getToken())) {
                String s = redisComponents.get(ADMIN_USER_TOKEN_PREFIX + userBase.getToken());
                AuthAdminUser authAdminUser = JsonUtils.toJavaObject(s, AuthAdminUser.class);
                if (Objects.nonNull(authAdminUser)) {
                    authAdminUser.setNickName(user.getNickName());
                    authAdminUser.setAvatar(propertiesComponents.getOssUrl() + avatar);
                    redisComponents.saveAsync(ADMIN_USER_TOKEN_PREFIX + userBase.getToken(), JsonUtils.toJSONString(authAdminUser));
                }
            }
        }
    }

    @Override
    public AdminUserLoginRes getUserSession(AuthAdminUser user) {
        AdminUserLoginRes adminUserLoginRes = new AdminUserLoginRes(
                user.getId(),
                user.getToken(),
                user.getNickName(),
                user.getAccount(),
                user.getAvatar(),
                user.getRoleId(),
                user.getRole(),
                getPermissionByRole(user.getRoleId(), user.getRole())
        );
        redisComponents.saveAsync(ADMIN_USER_TOKEN_PREFIX + user.getToken(), JsonUtils.toJSONString(adminUserLoginRes));
        return adminUserLoginRes;
    }

    private String getColumns(String username) {
        if (username.matches(TEL_PATTERN)) return PHONE;
        if (username.matches(MAIL_PATTERN)) return MAIL;
        return ACCOUNT;
    }
    @Override
    public Set<String> getPermissionByRole(Long roleId, String roleValue) {
        Set<String> menuPermission = Sets.newHashSet(ADMIN_SECURITY);
        if (roleValue.equals(SUPPER_ROLE)) return menuPermission;
        String permissionCache = redisComponents.get(ROLE_BUTTON_PREFIX + roleValue);
        if (StringUtils.isEmpty(permissionCache)) {
            menuPermission = adminMenuService.getButtonPermission(roleId);
            redisComponents.saveAsync(ROLE_BUTTON_PREFIX + roleValue, JsonUtils.toJSONString(menuPermission));
        } else {
            List<Object> objects = JsonUtils.toList(permissionCache);
            menuPermission = Objects.isNull(objects) ? Sets.newHashSet() : objects.stream().map(Object::toString).collect(Collectors.toSet());
        }
        return menuPermission;
    }

    @Override
    public void logout(AuthAdminUser user) {
        String token = user.getToken();
        AdminUser adminUser = AdminUser.builder().token("").build();
        adminUserRepository.update(adminUser, Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, user.getAccount()));
        redisComponents.deleteKey(ADMIN_USER_TOKEN_PREFIX + token);
    }
}
