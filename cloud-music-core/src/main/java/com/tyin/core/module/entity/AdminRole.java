package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/4/6 10:52
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRole extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String value;
    private String name;
    private String description;
    private Boolean disabled;
    private Integer sort;
}
