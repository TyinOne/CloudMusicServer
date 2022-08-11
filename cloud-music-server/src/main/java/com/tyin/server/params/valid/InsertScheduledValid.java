package com.tyin.server.params.valid;

import com.tyin.core.constants.ScheduleConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Tyin
 * @date 2022/7/14 10:45
 * @description ...
 */
@Data
public class InsertScheduledValid {
    /**
     * 任务名称
     */
    @NotBlank
    private String scheduledName;

    /**
     * 任务组名
     */
    @NotBlank
    private String scheduledGroup;

    /**
     * 调用目标字符串
     */
    @NotBlank
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @NotBlank
    private String cronExpression;

    /**
     * cron计划策略 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
     */
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private String concurrent;

    /**
     * 任务状态（0正常 1暂停）
     */
    private String status;
}
