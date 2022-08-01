package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/7/28 17:20
 * @description ...
 */
@Data
public class AdminInviteCodeRes {

    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("邀请码")
    private String code;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("是否无效")
    private Boolean invalid;
    @ApiModelProperty("是否使用")
    private Boolean used;
    @ApiModelProperty("创建者")
    private String createBy;
    @ApiModelProperty("使用者")
    private String useBy;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date created;
    @ApiModelProperty("过期时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date expirationTime;
}
