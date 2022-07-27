package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import com.tyin.core.utils.timer.task.AdminInviteCodeTaskState;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/7/21 15:29
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminInviteCode extends BaseEntity implements AdminInviteCodeTaskState, Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @Getter
    @Setter
    private Long roleId;
    @Setter
    private String code;
    /**
     * 0 有效， 1 过期的
     */
    @Getter
    @Setter
    private Integer status;
    @Setter
    private Date expirationTime;
    /**
     * 0 未使用， 1 已使用
     */
    @Getter
    @Setter
    private Boolean used;
    @Getter
    @Setter
    private String createBy;
    @Getter
    @Setter
    private String useBy;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Date getExpirationTime() {
        return this.expirationTime;
    }

    @Override
    public String getComponentName() {
        return AdminInviteCodeTaskState.super.getComponentName();
    }

    @Override
    public String getMethodName() {
        return AdminInviteCodeTaskState.super.getMethodName();
    }

    @Override
    public List<Object[]> getMethodParams() {
        return AdminInviteCodeTaskState.super.getMethodParams();
    }
}
