package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.repository.admin.AdminUserRepository;
import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

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

    @Override
    public AdminUserLoginRes login(AdminLoginParams adminLoginParams, Integer ipAddress) {
        String username = adminLoginParams.getUsername();
        String password = adminLoginParams.getPassword();
        password = StringUtils.sha256Encode(DigestUtils.md5Hex(password.getBytes(StandardCharsets.UTF_8))).toUpperCase(Locale.ROOT);
        AdminUser adminUser = adminUserRepository.selectOne(Wrappers.<AdminUser>query().eq(getColumns(username), username).lambda().eq(AdminUser::getPassword, password));
        Asserts.isTrue(Objects.nonNull(adminUser), AUTH_FAILED);
        Asserts.isTrue(!adminUser.getDisabled(), USER_DISABLED);
        String token = StringUtils.getUuid();
        redisComponents.save(ADMIN_USER_TOKEN_PREFIX + token, JsonUtils.toJSONString(AuthAdminUser.builder().token(token).name(adminUser.getName()).avatar(adminUser.getAvatar()).build()));
        return AdminUserLoginRes.builder().token(token).build();
    }

    private String getColumns(String username) {
        if (username.matches(TEL_PATTERN)) return "phone";
        if (username.matches(MAIL_PATTERN)) return "mail";
        return "account";
    }
}
