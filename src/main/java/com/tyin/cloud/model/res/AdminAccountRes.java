package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.cloud.core.utils.DateUtils;
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
    private String account;
    private String nickName;
    private String mail;
    private String phone;
    private Boolean disabled;
    private String roles;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date lastLoginTime;
}
