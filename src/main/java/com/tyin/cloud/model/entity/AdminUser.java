package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tyin
 * @date 2022/3/30 10:07
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AdminUser extends BaseEntity{
    @TableId(type = IdType.AUTO)
    private Long id;
}
