package com.tyin.server.service.impl;

import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.server.params.valid.InsertScheduledValid;
import com.tyin.server.repository.AdminScheduledLogRepository;
import com.tyin.server.repository.AdminScheduledRepository;
import com.tyin.server.service.IAdminScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Tyin
 * @date 2022/7/14 9:42
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminScheduledServiceImpl implements IAdminScheduledService {
    private final AdminScheduledLogRepository adminScheduledLogRepository;

    private final AdminScheduledRepository adminScheduledRepository;
    private final RedisComponents redisComponents;

    @Override
    public void addLog(AdminScheduledLog adminScheduledLog) {
        adminScheduledLogRepository.insert(adminScheduledLog);
    }

    @Override
    public Integer addScheduled(InsertScheduledValid valid) {
        AdminScheduled scheduled = AdminScheduled.builder().build();
        BeanUtils.copyProperties(valid, scheduled);
        return adminScheduledRepository.insert(scheduled);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void test() {
        redisComponents.get("Scheduled");
    }
}
