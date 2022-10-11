package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/7/28 17:20
 * @description ...
 */
@Data
public class AdminInviteCodeRes {

    @Schema(description = "id")
    private String id;
    @Schema(description = "邀请码")
    private String code;
    @Schema(description = "角色名称")
    private String roleName;
    @Schema(description = "是否无效")
    private Boolean invalid;
    @Schema(description = "是否使用")
    private Boolean used;
    @Schema(description = "创建者")
    private String createBy;
    @Schema(description = "使用者")
    private String useBy;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date created;
    @Schema(description = "过期时间")
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date expirationTime;
}
