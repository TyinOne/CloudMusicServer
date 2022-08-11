package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Tyin
 * @date 2022/7/8 9:27
 * @description ...
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminScheduledLog extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    private String scheduleName;

    /**
     * 任务组名
     */
    private String scheduleGroup;

    /**
     * 调用目标字符串
     */
    private String invokeTarget;

    /**
     * 任务信息
     */
    private String scheduleMessage;

    private Date startTime;

    private Date stopTime;

    private String exceptionInfo;

    private Boolean failed;
}
