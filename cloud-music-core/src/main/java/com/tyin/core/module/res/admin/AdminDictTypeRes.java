package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/6/9 9:47
 * @description ...
 */
@Data
public class AdminDictTypeRes {
    private Long id;
    private String dictType;
    private String dictLabel;
    private String dictDescription;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date created;
    @ApiModelProperty("字典状态")
    private Boolean deleted;
}
