package com.tyin.core.module.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.core.module.base.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/30 10:07
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AdminUser extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 登录验证
     */
    private String token;

    /**
     * 用户名
     */
    private String account;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * password
     */
    private String password;

    private Boolean disabled;

    private Long lastLogin;

    private Date lastLoginTime;

}
