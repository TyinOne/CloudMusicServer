package com.tyin.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.core.api.PageResult;
import com.tyin.core.auth.admin.TokenService;
import com.tyin.core.auth.admin.UserDetailsServiceImpl;
import com.tyin.core.auth.admin.utils.LoginUtils;
import com.tyin.core.components.PropertiesComponents;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.constants.ResMessageConstants;
import com.tyin.core.constants.SecurityConstantKey;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.*;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.module.valid.AdminRegisterValid;
import com.tyin.core.module.valid.SaveAccountValid;
import com.tyin.core.module.valid.UpdatePasswordValid;
import com.tyin.core.repository.admin.AdminUserExtraRepository;
import com.tyin.core.repository.admin.AdminUserRepository;
import com.tyin.core.service.IAdminInviteCodeService;
import com.tyin.core.service.IAdminMenuService;
import com.tyin.core.service.IAdminRoleService;
import com.tyin.core.service.IAdminUserService;
import com.tyin.core.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tyin.core.constants.PermissionConstants.ADMIN_SECURITY;
import static com.tyin.core.constants.PermissionConstants.SUPPER_ROLE;
import static com.tyin.core.constants.PropertiesKeyConstants.*;
import static com.tyin.core.constants.RedisKeyConstants.ROLE_BUTTON_PREFIX;
import static com.tyin.core.constants.ResMessageConstants.UPDATE_FAILED;

/**
 * @author Tyin
 * @date 2022/3/31 13:45
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminUserServiceImpl implements IAdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserExtraRepository adminUserExtraRepository;
    private final RedisComponents redisComponents;
    private final PropertiesComponents propertiesComponents;
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;
    private final IAdminInviteCodeService adminInviteCodeService;
    private final TokenService tokenService;

    @Override
    public AdminUserDetailRes getUserInfo(AuthAdminUser user) {
        String account = user.getAccount();
        String ossUrl = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_HOST);
        return adminUserRepository.selectUserDetail(account, ossUrl);
    }

    @Override
    public AdminUser getUserEntity(String account) {
        return adminUserRepository.selectOne(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, account));
    }

    @Override
    public PageResult<AdminAccountRes, ?> getUserList(Long size, Long current, String name, Long roleId, Long disabled) {
        QueryWrapper<AdminUser> wrapper = new QueryWrapper<>();
        wrapper.lambda().apply(disabled != -1, " `user`.`disabled` = {0} ", disabled).apply(roleId != 0, " `role`.`id` = {0}", roleId).apply(" `role`.`deleted` = {0}", 0).apply(" `u_role`.`deleted` = {0}", 0).and(StringUtils.isNotEmpty(name), i -> i.apply(" INSTR(`user`.`nick_name`, {0}) > 0", name).or().apply(" INSTR(`extra`.`id_card_name`, {0}) > 0", name));
        IPage<AdminAccountRes> resPage = adminUserRepository.selectUserList(new Page<>(current, size), wrapper);
        return PageResult.buildResult(resPage);
    }

    @Override
    public AdminAccountDetailRes getAccountDetail(String account) {
        AdminAccountDetailRes res = adminUserRepository.selectAccountDetail(account);
        res.setAvatar(propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_HOST) + res.getAvatar());
        res.setLastLogin(IpUtils.longToIp(Long.parseLong(res.getLastLogin())));
        List<AdminRole> roles = adminRoleService.getRoles(res.getId());
        if (Objects.nonNull(roles)) res.setRoles(roles.stream().map(AdminRole::getValue).collect(Collectors.toSet()));
        return res;
    }

    @Override
    public void saveAccountInfo(SaveAccountValid valid) {
        //更新数据库
        AdminUser userBase = getUserEntity(valid.getAccount());
        AdminUser user = AdminUser.builder().nickName(valid.getNickName()).mail(valid.getMail()).phone(valid.getPhone()).build();
        String avatar;
        if (StringUtils.isNotEmpty(valid.getAvatar().getFileName())) {
            SaveAccountValid.AvatarUpdate avatarUpdate = valid.getAvatar();
            String avatarUri = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_URI_IMAGES) + userBase.getAccount() + "/avatar/";
            String tmpPath = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_SERVER_URI) + propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_FILE_URI_TEMP) + avatarUpdate.getFileName();
            String pathDir = propertiesComponents.getConfigByKey(PropertiesEnum.OSS, OSS_SERVER_URI) + avatarUri;
            File source = new File(tmpPath);
            File target = new File(pathDir);
            //复制文件
            try {
                FileUtils.copyFileToDirectory(source, target);
                avatar = avatarUri + avatarUpdate.getFileName();
                user.setAvatar(avatar);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        adminUserRepository.update(user, Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, valid.getAccount()));
        AdminUserExtra userExtra = AdminUserExtra.builder().birth(valid.getBirth()).region(valid.getRegion()).build();
        adminUserExtraRepository.update(userExtra, Wrappers.<AdminUserExtra>lambdaQuery().eq(AdminUserExtra::getUserId, userBase.getId()));
        //更新AdminRes
        Set<String> roles = valid.getRoles();
        if (roles.size() > 0) {
            //移除当前拥有的角色配置
            Integer removeRow = adminRoleService.removeAllRoleByUserId(userBase.getId());
            roles.forEach(role -> {
                Integer row = adminRoleService.addUserRoleKey(valid.getAccount(), role);
            });
            //强制下线 TODO 可重新调用登录接口保存最新信息
            if (StringUtils.isNotEmpty(userBase.getToken())) {
                redisComponents.deleteKey(SecurityConstantKey.LOGIN_TOKEN_KEY + userBase.getToken());
            }
        }
    }

    @Override
    public AdminUserLoginRes getUserSession(AuthAdminUser user) {
        AdminUserLoginRes adminUserLoginRes = (AdminUserLoginRes) user;
        return tokenService.verifyToken(adminUserLoginRes);
    }

    @Override
    public Set<String> getPermissionByRole(Set<String> roles) {
        Set<String> menuPermission = Sets.newHashSet(ADMIN_SECURITY);
        if (roles.contains(SUPPER_ROLE)) return menuPermission;
        List<String> permissionCaches = Lists.newArrayList();
        for (String roleValue : roles) {
            String cacheItem = redisComponents.get(ROLE_BUTTON_PREFIX + roleValue);
            if (StringUtils.isNotEmpty(cacheItem)) {
                permissionCaches.add(cacheItem);
            }
        }
        if (permissionCaches.isEmpty()) {
            for (String roleValue : roles) {
                menuPermission.addAll(adminMenuService.getButtonPermission(roleValue));
                redisComponents.saveAsync(ROLE_BUTTON_PREFIX + roleValue, JsonUtils.toJSONString(menuPermission));
            }
        } else {
            for (String permissionCache : permissionCaches) {
                List<Object> objects = JsonUtils.toList(permissionCache);
                menuPermission.addAll(Objects.isNull(objects) ? Sets.newHashSet() : objects.stream().map(Object::toString).collect(Collectors.toSet()));
            }
        }
        return menuPermission;
    }

    @Override
    public void logout(AuthAdminUser user) {
        AdminUser adminUser = AdminUser.builder().token("").build();
        adminUserRepository.update(adminUser, Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, user.getAccount()));
        redisComponents.deleteKey(SecurityConstantKey.LOGIN_TOKEN_KEY + user.getRedisKey());
    }

    @Override
    public InviteCodeBean generateInviteCode(Long id, AuthAdminUser user) {
        return adminInviteCodeService.generateInviteCode(id, user);
    }

    @Override
    @Async
    public void updateToken(String account, String key, Long ipAddress) {
        AdminUser user = AdminUser.builder().token(key).lastLogin(ipAddress).lastLoginTime(DateUtils.getNowDate()).build();
        adminUserRepository.update(user, Wrappers.<AdminUser>update().eq(LoginUtils.getColumns(account), account));
    }

    @Override
    public void resetPassword(UpdatePasswordValid valid) {
        String newPassword = SpringUtils.getBean(UserDetailsServiceImpl.class).getPassword(valid.getPassword());
        AdminUser adminUser = getUserEntity(valid.getAccount());
        adminUser.setPassword(newPassword);
        int update = adminUserRepository.updateById(adminUser);
        String key = SecurityConstantKey.LOGIN_TOKEN_KEY + adminUser.getAccount();
        adminUserRepository.update(adminUser, Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, adminUser.getAccount()));
        redisComponents.deletePrefixKey(key);
        Asserts.isTrue(update == 1, UPDATE_FAILED);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(AdminRegisterValid adminRegisterValid) {
        String code = adminRegisterValid.getCode();
        //验证邀请码是否有效
        AdminInviteCode adminInviteCode = adminInviteCodeService.getInviteCode(code);
        Asserts.isTrue(Objects.nonNull(adminInviteCode), ResMessageConstants.INVALID_CODE);
        String account = adminRegisterValid.getAccount();
        String password = adminRegisterValid.getPassword();
        password = StringUtils.sha256Encode(DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8))).toUpperCase(Locale.ROOT);
        Long aLong = adminUserRepository.selectCount(Wrappers.<AdminUser>lambdaQuery().eq(AdminUser::getAccount, account));
        Asserts.isTrue(aLong == 0, ResMessageConstants.ACCOUNT_REPEAT);
        String defaultAvatar = propertiesComponents.getConfigByKey(PropertiesEnum.ADMIN, DEFAULT_AVATAR);
        AdminUser adminUser = AdminUser.builder().account(account).nickName(account).password(password).avatar(defaultAvatar).build();
        AdminUserExtra adminUserExtra = AdminUserExtra.builder().userId(adminUser.getId()).build();
        Asserts.isTrue(adminUserRepository.insert(adminUser) == 1 && adminUserExtraRepository.insert(adminUserExtra) == 1 && adminRoleService.addUserRole(adminUser.getId(), adminInviteCode.getRoleId()) == 1, ResMessageConstants.REGISTER_FAILED);
        adminInviteCodeService.handleInviteCodeUse(code, account);
    }
}
