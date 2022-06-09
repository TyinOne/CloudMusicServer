package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.cloud.model.base.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/5/12 0:23
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDictType extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String dictType;
    private String dictLabel;
    private String dictDescription;
}
