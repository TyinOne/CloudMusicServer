package com.tyin.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.components.CloudTimerTaskComponents;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.components.properties.models.AdminConfig;
import com.tyin.core.constants.RedisKeyConstants;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.module.res.admin.AdminInviteCodeRes;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.api.PageResult;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.repository.AdminInviteCodeRepository;
import com.tyin.server.service.IAdminInviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/7/22 18:02
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminInviteCodeServiceImpl implements IAdminInviteCodeService {
    private final RedisComponents redisComponents;

    private final AdminInviteCodeRepository adminInviteCodeRepository;

    private final CloudTimerTaskComponents cloudTimerTaskComponents;

    private final PropertiesComponents propertiesComponents;

    @Override
    public InviteCodeBean generateInviteCode(Long id, AuthAdminUser user) {
        AdminConfig adminConfig = propertiesComponents.getAdminConfig();
        Integer configExpire = adminConfig.getInviteCodeExpiration();
        int subStart = (int) (Math.random() * 24) + 1;
        int subEnd = subStart + 6;
        String code = StringUtils.sha256Encode(StringUtils.getUuid() + user.getAccount() + System.currentTimeMillis()).substring(subStart, subEnd).toUpperCase();
        String inviteCodeKey = RedisKeyConstants.INVITE_CODE_EXPIRE + user.getAccount() + ":" + id + ":" + code;
        long expiration = System.currentTimeMillis() + configExpire * 60 * 1000;
        Date expirationDate = new Date(expiration);
        InviteCodeBean build = InviteCodeBean.builder().code(code).expiration(configExpire * 60 * 1000L).build();
        redisComponents.saveAsync(inviteCodeKey, JsonUtils.toJSONString(build));
        redisComponents.expireKeyAt(inviteCodeKey, expirationDate);
        AdminInviteCode adminInviteCode = AdminInviteCode.builder()
                .roleId(id)
                .code(code)
                .invalid(Boolean.FALSE)
                .used(Boolean.FALSE)
                .createBy(user.getAccount())
                .expirationTime(expirationDate)
                .build();
        adminInviteCodeRepository.insert(adminInviteCode);
        cloudTimerTaskComponents.addTask(adminInviteCode);
        return build;
    }

    @Override
    public void handleInviteCodeExpire(String code) {
        AdminInviteCode inviteCode = selectInviteCode(code);
        inviteCode.setInvalid(Boolean.TRUE);
        adminInviteCodeRepository.deleteById(inviteCode);
        String inviteCodeKey = RedisKeyConstants.INVITE_CODE_EXPIRE + inviteCode.getCreateBy() + ":" + inviteCode.getRoleId() + ":" + code;
        redisComponents.deleteKey(inviteCodeKey);
    }
    private AdminInviteCode selectInviteCode(String code) {
        return adminInviteCodeRepository.selectOne(Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getCode, code));
    }

    @Override
    public void handleInviteCodeUse(String code, String account) {
        AdminInviteCode adminInviteCode = adminInviteCodeRepository.selectOne(Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getCode, code).eq(AdminInviteCode::getInvalid, Boolean.FALSE));
        adminInviteCode.setUsed(Boolean.TRUE);
        adminInviteCode.setInvalid(Boolean.TRUE);
        adminInviteCode.setUseBy(account);
        adminInviteCodeRepository.updateById(adminInviteCode);
        cloudTimerTaskComponents.remove(code);
        redisComponents.deleteKey(RedisKeyConstants.INVITE_CODE_EXPIRE + adminInviteCode.getCreateBy() + ":" + adminInviteCode + ":" + code);
    }

    @Override
    public AdminInviteCode getInviteCode(String code) {
        return adminInviteCodeRepository.selectOne(Wrappers.<AdminInviteCode>lambdaQuery()
                .eq(AdminInviteCode::getCode, code)
                .eq(AdminInviteCode::getUsed, Boolean.FALSE)
                .eq(AdminInviteCode::getInvalid, Boolean.FALSE)
        );
    }

    @Override
    public PageResult<AdminInviteCodeRes, ?> getList(String useBy, String createBy, Boolean invalid, Boolean isUsed, Long current, Long size) {
        IPage<AdminInviteCodeRes> res = adminInviteCodeRepository.selectResList(new Page<>(current, size),
                Wrappers.<AdminInviteCode>lambdaQuery()
                        .apply(StringUtils.isNotEmpty(useBy), "INSTR(`use_by`, {0}) > 0", useBy)
                        .apply(StringUtils.isNotEmpty(createBy), "INSTR(`create_by`, {0}) > 0", createBy)
                        .eq(Objects.nonNull(invalid), AdminInviteCode::getInvalid, invalid)
                        .eq(Objects.nonNull(isUsed), AdminInviteCode::getUsed, isUsed)
                        .eq(AdminInviteCode::getDeleted, Boolean.FALSE)
        );
        return PageResult.buildResult(res);
    }

    @Override
    public Integer remove(Long id) {
        AdminInviteCode adminInviteCode = adminInviteCodeRepository.selectById(id);
        int row = adminInviteCodeRepository.deleteById(id);
        handleInviteCodeExpire(adminInviteCode.getCode());
        return row;
    }
}
