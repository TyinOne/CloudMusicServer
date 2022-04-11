package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.cloud.model.base.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
public class AdminUserRole extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long roleId;
}
