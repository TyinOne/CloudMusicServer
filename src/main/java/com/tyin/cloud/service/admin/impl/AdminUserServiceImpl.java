package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Sets;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.IpUtils;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.entity.AdminUserDetailRes;
import com.tyin.cloud.model.entity.AdminUserExtra;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminAccountDetailRes;
import com.tyin.cloud.model.res.AdminAccountRes;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.valid.SaveAccountValid;
import com.tyin.cloud.repository.admin.AdminUserExtraRepository;
import com.tyin.cloud.repository.admin.AdminUserRepository;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRoleService;
import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

import static com.tyin.cloud.core.constants.ParamsConstants.*;
import static com.tyin.cloud.core.constants.PatternConstants.MAIL_PATTERN;
import static com.tyin.cloud.core.constants.PatternConstants.TEL_PATTERN;
import static com.tyin.cloud.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.cloud.core.constants.PermissionConstants.SUPPER_ROLE;
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
        adminUserRepository.updateById(adminUser);
        AdminRole role = adminRoleService.getRoles(adminUser.getId());
        HashSet<String> permissions = role.getValue().equals(SUPPER_ROLE) ? Sets.newHashSet(ADMIN_SECURITY) : adminMenuService.getMenuPermission(adminUser);
        AuthAdminUser user = AuthAdminUser.builder()
                .token(token).nickName(adminUser.getNickName()).account(adminUser.getAccount()).avatar(avatar).permissions(permissions).role(role.getValue())
                .build();
        redisComponents.save(ADMIN_USER_TOKEN_PREFIX + token, JsonUtils.toJSONString(user));
        return new AdminUserLoginRes(token,
                adminUser.getNickName(),
                user.getAccount(),
                avatar,
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
        if (StringUtils.isNotEmpty(valid.getAvatar().getFileName())) {
            SaveAccountValid.AvatarUpdate avatarUpdate = valid.getAvatar();
            String avatarUri = propertiesComponents.getOssImages() + userBase.getAccount() + "/avatar/";
            String tmpPath = propertiesComponents.getOssServer() + propertiesComponents.getOssTmp() + avatarUpdate.getFileName();
            String pathDir = propertiesComponents.getOssServer() + avatarUri;
            File source = new File(tmpPath);
            File target = new File(pathDir);
            try {
                FileUtils.copyFileToDirectory(source, target);
                user.setAvatar(avatarUri + avatarUpdate.getFileName());
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
                    authAdminUser.setAvatar(propertiesComponents.getOssUrl() + user.getAvatar());
                    redisComponents.save(ADMIN_USER_TOKEN_PREFIX + userBase.getToken(), JsonUtils.toJSONString(authAdminUser));
                }
            }
        }
    }

    private String getColumns(String username) {
        if (username.matches(TEL_PATTERN)) return PHONE;
        if (username.matches(MAIL_PATTERN)) return MAIL;
        return ACCOUNT;
    }
}
