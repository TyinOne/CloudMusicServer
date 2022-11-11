package com.tyin.core.components;

import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.core.utils.scheduled.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/7/7 16:53
 * @description ...
 */
@Component
@RequiredArgsConstructor
public abstract class ScheduledComponents {

    private final Scheduler scheduler;

    public void init(List<AdminScheduled> scheduledList) throws SchedulerException {
        scheduler.clear();
        for (AdminScheduled scheduled : scheduledList) {
            ScheduleUtils.createScheduleJob(scheduler, scheduled);
        }
    }

    public void addScheduled(AdminScheduled adminScheduled) throws SchedulerException {
        ScheduleUtils.createScheduleJob(scheduler, adminScheduled);
    }

    /**
     * 添加定时任务执行日志
     *
     * @param adminScheduledLog log
     */
    public abstract void addLog(AdminScheduledLog adminScheduledLog);

    public void updateScheduled(AdminScheduled adminScheduled, String group) throws SchedulerException {
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(adminScheduled.getId(), group);
        if (scheduler.checkExists(jobKey)) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, adminScheduled);
    }
}
