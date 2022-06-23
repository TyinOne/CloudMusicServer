package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/4/9 4:45
 * @description ...
 */
@Data
public class AdminRoleRes {
    private Long id;
    private String value;
    private String name;
    private String description;
    private Boolean disabled;
    private Integer sort;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date created;
}
