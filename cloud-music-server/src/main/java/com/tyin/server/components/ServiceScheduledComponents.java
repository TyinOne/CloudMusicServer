package com.tyin.server.components;

import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.core.service.IAdminScheduledService;
import com.tyin.core.utils.SpringUtils;
import org.quartz.Scheduler;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/7/14 9:45
 * @description ...
 */
@Component
public class ServiceScheduledComponents extends ScheduledComponents {

    public ServiceScheduledComponents(Scheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void addLog(AdminScheduledLog adminScheduledLog) {
        SpringUtils.getBean(IAdminScheduledService.class).addLog(adminScheduledLog);
    }
}
