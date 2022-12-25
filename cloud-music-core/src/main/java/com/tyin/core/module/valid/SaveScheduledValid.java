package com.tyin.core.module.valid;

import com.tyin.core.constants.ScheduleConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/11/10 11:26
 * @description ...
 */
@Data
public class SaveScheduledValid {
    private Long id;
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
    private Boolean concurrent;

    private Boolean disabled;
}
