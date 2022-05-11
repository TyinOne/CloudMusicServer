package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tyin.cloud.core.utils.DateUtils;
import com.tyin.cloud.model.bean.RoleLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/6 15:20
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminAccountDetailRes {
    @JsonIgnore
    private Long id;
    private String account;
    private String nickName;
    private String avatar;
    private String phone;
    private String mail;
    private String lastLogin;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS)
    private Date lastLoginTime;
    private Integer sex;
    private Integer age;
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD)
    private Date birth;
    private String region;
    private String idCardNo;
    private String idCardName;
    private String idCardAddress;
    private List<RoleLabel> roles;

}