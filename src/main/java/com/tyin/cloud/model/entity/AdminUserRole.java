package com.tyin.cloud.model.entity;

import lombok.*;

/**
 * @author Tyin
 * @date 2022/4/6 10:51
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserRole extends BaseEntity{
    private Long id;
    private Long userId;
    private Long roleId;
}
