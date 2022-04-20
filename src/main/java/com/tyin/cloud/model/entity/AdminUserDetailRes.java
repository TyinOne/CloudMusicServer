package com.tyin.cloud.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String name;
    private String avatar;
    private String phone;
    private String mail;
    private String role;
    private String area;
    private String ipAddress;
}
