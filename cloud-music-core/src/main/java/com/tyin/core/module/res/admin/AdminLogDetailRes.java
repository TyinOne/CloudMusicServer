package com.tyin.core.module.res.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.tyin.core.utils.DateUtils.YYYY_MM_DD;

/**
 * @author Tyin
 * @date 2022/10/13 17:35
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminLogDetailRes {

    private Long id;
    @JsonFormat(pattern = YYYY_MM_DD)
    private Date time;
    private String uri;
    private String params;
    private String result;
    private String address;
    private String account;
}
