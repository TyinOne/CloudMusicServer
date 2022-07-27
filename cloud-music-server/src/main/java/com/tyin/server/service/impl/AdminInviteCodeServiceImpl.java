package com.tyin.server.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyin.core.components.CloudTimerTaskComponents;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.constants.RedisKeyConstants;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.repository.AdminInviteCodeRepository;
import com.tyin.server.service.IAdminInviteCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/7/22 18:02
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminInviteCodeServiceImpl implements IAdminInviteCodeService {
    private final RedisComponents redisComponents;

    private final AdminInviteCodeRepository adminInviteCodeRepository;

    private final CloudTimerTaskComponents cloudTimerTaskComponents;

    @Override
    public InviteCodeBean generateInviteCode(Long id, AuthAdminUser user) {
        int configExpire = 2;
        String code = StringUtils.sha256Encode(StringUtils.getUuid() + user.getAccount() + System.currentTimeMillis()).substring(4, 10).toUpperCase();
        String inviteCodeKey = RedisKeyConstants.INVITE_CODE_EXPIRE + user.getAccount() + ":" + id + ":" + code;
        long expiration = System.currentTimeMillis() + configExpire * 60 * 1000;
        Date expirationDate = new Date(expiration);
        InviteCodeBean build = InviteCodeBean.builder().code(code).expiration(configExpire * 60 * 1000L).build();
        redisComponents.saveAsync(inviteCodeKey, JsonUtils.toJSONString(build));
        redisComponents.expireKeyAt(inviteCodeKey, expirationDate);
        AdminInviteCode adminInviteCode = AdminInviteCode.builder()
                .roleId(id)
                .code(code)
                .status(0)
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
        adminInviteCodeRepository.update(AdminInviteCode.builder().status(1).build(), Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getCode, code).eq(AdminInviteCode::getStatus, 0));
        System.out.println("code:" + code);
    }

    @Override
    public void handleInviteCodeUse(String code, String account) {
        AdminInviteCode adminInviteCode = adminInviteCodeRepository.selectOne(Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getCode, code).eq(AdminInviteCode::getStatus, 0));
        adminInviteCode.setUsed(Boolean.TRUE);
        adminInviteCode.setStatus(1);
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
                .eq(AdminInviteCode::getStatus, 0)
        );
    }
}
