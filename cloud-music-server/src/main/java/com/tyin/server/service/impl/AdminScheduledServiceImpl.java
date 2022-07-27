package com.tyin.server.service.impl;

import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.server.params.valid.InsertScheduledValid;
import com.tyin.server.repository.AdminScheduledLogRepository;
import com.tyin.server.repository.AdminScheduledRepository;
import com.tyin.server.service.IAdminScheduledService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author Tyin
 * @date 2022/7/14 9:42
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminScheduledServiceImpl implements IAdminScheduledService {
    private final AdminScheduledLogRepository adminScheduledLogRepository;

    private final AdminScheduledRepository adminScheduledRepository;
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
}
