package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import lombok.Data;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/5/5 9:04
 * @description ...
 */
@Data
public class AdminLogRes {
    private Long id;
    private String uri;
    private String ip;
    private String method;
    private String requestMethod;
    private Boolean status;
    //    太多了影响速度 放到详情里面
//    private String params;
//    private String result;
    private String elapsed;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date created;
}
