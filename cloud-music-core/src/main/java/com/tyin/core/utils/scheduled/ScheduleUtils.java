package com.tyin.core.utils.scheduled;

import com.tyin.core.constants.ScheduleConstants;
import com.tyin.core.exception.ApiException;
import com.tyin.core.module.entity.AdminScheduled;
import org.quartz.*;

/**
 * @author Tyin
 * @date 2022/7/7 17:01
 * @description ...
 */
public class ScheduleUtils {

    private static Class<? extends Job> getQuartzJobClass(AdminScheduled scheduled) {
        return scheduled.getConcurrent() ? QuartzDisallowConcurrentExecution.class : QuartzJobExecution.class;
    }

    public static void createScheduleJob(Scheduler scheduler, AdminScheduled adminScheduled) throws SchedulerException {
        Class<? extends Job> jobClass = getQuartzJobClass(adminScheduled);
        // 构建job信息
        Long scheduledId = adminScheduled.getId();
        String scheduledGroup = adminScheduled.getScheduledGroup();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(adminScheduled.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(adminScheduled, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduledId, scheduledGroup))
                .withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ScheduleConstants.TASK_PROPERTIES, adminScheduled);
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(scheduledId, scheduledGroup))
                .usingJobData(jobDataMap)
                .build();

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(scheduledId, scheduledGroup))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(scheduledId, scheduledGroup));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (adminScheduled.getDisabled().equals(ScheduleConstants.Status.PAUSE.getValue())) {
            scheduler.pauseJob(ScheduleUtils.getJobKey(scheduledId, scheduledGroup));
        }
    }

    public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(AdminScheduled adminScheduled, CronScheduleBuilder cb) {
        return switch (adminScheduled.getMisfirePolicy()) {
            case ScheduleConstants.MISFIRE_DEFAULT -> cb;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES -> cb.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED -> cb.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING -> cb.withMisfireHandlingInstructionDoNothing();
            default -> throw new ApiException("The task misfire policy '" + adminScheduled.getMisfirePolicy()
                    + "' cannot be used in cron schedule tasks");
        };
    }
}
