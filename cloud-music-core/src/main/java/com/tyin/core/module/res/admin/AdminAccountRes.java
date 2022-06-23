package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("用户名")
    private String account;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("号码")
    private String phone;
    @ApiModelProperty("是否禁用")
    private Boolean disabled;
    @ApiModelProperty("角色")
    private String roles;
    @ApiModelProperty("最后登录时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date lastLoginTime;
}
