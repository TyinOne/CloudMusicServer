package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.cloud.model.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Tyin
 * @date 2022/4/23 12:11
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AdminUserExtra extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Integer sex;
    private Date birth;
    private String region;
    private String idCardNo;
    private String idCardName;
    private String idCardAddress;
}
