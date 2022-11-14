package com.tyin.core.utils.scheduled;

import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.constants.ScheduleConstants;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.core.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/7/7 17:34
 * @description ...
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {
    private static final ThreadLocal<Date> THREAD_LOCAL = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) {
        AdminScheduled scheduled = (AdminScheduled) context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES);
        try {
            before(context, scheduled);
            doExecute(context, scheduled.getInvokeTarget());
            after(context, scheduled, null);
            log.info("任务已执行");
        } catch (Exception e) {
            log.error("任务执行异常  - ：", e.fillInStackTrace());
            after(context, scheduled, e);
        }
    }

    /**
     * 执行前
     *
     * @param context   工作执行上下文对象
     * @param scheduled 系统计划任务
     */
    protected void before(JobExecutionContext context, AdminScheduled scheduled) {
        THREAD_LOCAL.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context   工作执行上下文对象
     * @param scheduled 系统计划任务
     */
    @Async
    protected void after(JobExecutionContext context, AdminScheduled scheduled, Exception e) {
        Date startTime = THREAD_LOCAL.get();
        THREAD_LOCAL.remove();

        final AdminScheduledLog adminScheduledLog = new AdminScheduledLog();
        adminScheduledLog.setScheduledName(scheduled.getScheduledName());
        adminScheduledLog.setScheduledGroup(scheduled.getScheduledGroup());
        adminScheduledLog.setInvokeTarget(scheduled.getInvokeTarget());
        adminScheduledLog.setStartTime(startTime);
        adminScheduledLog.setStopTime(new Date());
        long runMs = adminScheduledLog.getStopTime().getTime() - adminScheduledLog.getStartTime().getTime();
        adminScheduledLog.setScheduledMessage(adminScheduledLog.getScheduledMessage() + "; 总共耗时：" + runMs + "毫秒");
        if (Objects.nonNull(e)) {
            adminScheduledLog.setFailed(Boolean.FALSE);
            String errorMsg = e.getMessage().trim();
            adminScheduledLog.setExceptionInfo(errorMsg);
        } else {
            adminScheduledLog.setFailed(Boolean.TRUE);
        }

        // 写入数据库当中
        SpringUtils.getBean(ScheduledComponents.class).addLog(adminScheduledLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context      工作执行上下文对象
     * @param invokeTarget 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, String invokeTarget) throws Exception;
}
