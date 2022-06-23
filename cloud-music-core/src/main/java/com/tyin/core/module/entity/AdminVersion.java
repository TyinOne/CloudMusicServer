package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import lombok.*;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/6/2 13:55
 * @description ...
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminVersion extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long version;
    private String src;
    private Date releaseTime;
    private Boolean forced;
    private Boolean latest;
    private String hash;
    private String md5;
    private String updateLog;
}
