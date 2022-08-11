package com.tyin.core.module.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/7/21 10:01
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteCodeBean {
    private String code;
    private Long expiration;
}
