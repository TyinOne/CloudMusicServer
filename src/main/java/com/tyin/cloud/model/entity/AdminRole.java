package com.tyin.cloud.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tyin
 * @date 2022/4/6 10:52
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminRole extends BaseEntity {
    private Long id;
    private String name;
    private Boolean disabled;
}
