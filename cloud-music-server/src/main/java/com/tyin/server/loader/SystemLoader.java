package com.tyin.server.loader;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.tyin.core.components.CloudTimerTaskComponents;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.base.TimerTaskState;
import com.tyin.core.module.entity.AdminDict;
import com.tyin.core.module.entity.AdminDictType;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.repository.admin.AdminDictRepository;
import com.tyin.core.repository.admin.AdminDictTypeRepository;
import com.tyin.core.repository.admin.AdminInviteCodeRepository;
import com.tyin.core.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tyin.core.constants.RedisKeyConstants.SYSTEM_CONFIG;

/**
 * @author Tyin
 * @date 2022/5/22 5:36
 * @description ...
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SystemLoader {
    private final CloudTimerTaskComponents timerTaskComponents;
    private final AdminDictRepository adminDictRepository;
    private final AdminDictTypeRepository adminDictTypeRepository;
    private final AdminInviteCodeRepository adminInviteCodeRepository;
    private final RedisComponents redisComponents;

    @Async
    public void startTimerTask() {
        List<TimerTaskState> list = Lists.newArrayList();
        //把数据库已使用或者已过期的状态改为失效
        //AdminInviteCode任务
        adminInviteCodeRepository.update(AdminInviteCode.builder().invalid(Boolean.TRUE).build(), Wrappers.<AdminInviteCode>lambdaUpdate()
                .eq(AdminInviteCode::getInvalid, Boolean.FALSE)
                .le(AdminInviteCode::getExpirationTime, DateUtils.getNowDate()));
        List<AdminInviteCode> adminInviteCode = adminInviteCodeRepository.selectList(Wrappers.<AdminInviteCode>lambdaQuery().eq(AdminInviteCode::getInvalid, Boolean.FALSE));
        list.addAll(adminInviteCode);
        timerTaskComponents.init(list);
    }

    @Async
    public void initDict() {
        List<AdminDictType> adminDictTypes = adminDictTypeRepository.selectList(Wrappers.emptyWrapper());
        adminDictTypes.forEach(type -> {
            List<AdminDict> adminDicts = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, type.getDictType()));
            adminDicts.forEach(item -> redisComponents.save(SYSTEM_CONFIG + type.getDictType() + ":" + item.getDictKey(), item.getDictValue()));
        });
    }
}