package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String params;
    private String result;
    private String elapsed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created;
}
