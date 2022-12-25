package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/3/30 23:21
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminRequestLog extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String uri;
    private Long ip;
    private String requestMethod;
    private String method;
    private String params;
    private String result;
    private Long elapsed;
    private Boolean isLogin;
    private String account;
    private Boolean status;
    private String headers;
}
