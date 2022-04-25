package com.tyin.cloud.model.entity;

import com.tyin.cloud.model.base.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author Tyin
 * @date 2022/4/22 11:38
 * @description ...
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRegion extends BaseEntity {
    private Long id;
    private Long parentId;
    private String name;
    private String fullName;
    private Integer level;
    private BigDecimal lat;
    private BigDecimal lng;
}
