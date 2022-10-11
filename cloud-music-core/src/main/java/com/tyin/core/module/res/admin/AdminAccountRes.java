package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/5/5 20:26
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAccountRes {
    @Schema(description = "用户名")
    private String account;
    @Schema(description = "昵称")
    private String nickName;
    @Schema(description = "邮箱")
    private String mail;
    @Schema(description = "号码")
    private String phone;
    @Schema(description = "是否禁用")
    private Boolean disabled;
    @Schema(description = "角色")
    private String roles;
    @Schema(description = "最后登录时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date lastLoginTime;
}
