package com.tyin.core.module.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tyin.core.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/4/19 14:37
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDetailRes {
    private String account;
    private String nickName;
    private String avatar;
    private String phone;
    private String mail;
    private Integer sex;
    private Integer age;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date birth;
    private String role;
    private String region;
    private String ipAddress;
    private String idCardNo;
    private String idCardAddress;
}
