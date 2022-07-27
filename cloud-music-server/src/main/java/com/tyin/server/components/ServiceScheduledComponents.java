package com.tyin.server.components;

import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.server.service.IAdminScheduledService;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/7/14 9:45
 * @description ...
 */
@Component
public class ServiceScheduledComponents extends ScheduledComponents {
    private final IAdminScheduledService adminScheduledService;

    public ServiceScheduledComponents(Scheduler scheduler, IAdminScheduledService adminScheduledService) {
        super(scheduler);
        this.adminScheduledService = adminScheduledService;
    }

    @Override
    public void addLog(AdminScheduledLog adminScheduledLog) {
        adminScheduledService.addLog(adminScheduledLog);
    }
}
