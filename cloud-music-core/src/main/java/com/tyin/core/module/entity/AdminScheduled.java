package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.constants.ScheduleConstants;
import com.tyin.core.module.base.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/7/7 16:56
 * @description ...
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminScheduled extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String scheduledName;

    /**
     * 任务组名
     */
    private String scheduledGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * cron计划策略 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
     */
    private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

    /**
     * 是否并发执行（0允许 1禁止）
     */
    private Boolean concurrent;

    /**
     * 任务状态（0正常 1暂停）
     */
    private Boolean disabled;

    /**
     * remark
     */
    private String remark;
}
