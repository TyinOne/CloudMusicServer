package com.tyin.core.module.res.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/11/11 14:07
 * @description id: null,
 * scheduledName: '',
 * scheduledGroup: '',
 * invokeTarget: '',
 * cronExpression: '',
 * misfirePolicy: '0',
 * concurrent: false,
 * disabled: false
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminScheduledDetail {
    private Long id;
    private String scheduledName;
    private String scheduledGroup;
    private String invokeTarget;
    private String cronExpression;
    private String misfirePolicy;
    private Boolean concurrent;
    private Boolean disabled;
}
