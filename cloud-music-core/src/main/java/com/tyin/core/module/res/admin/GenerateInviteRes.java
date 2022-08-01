package com.tyin.core.module.res.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/8/1 10:48
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateInviteRes {
    private String code;
    private String time;
    private String message;
}
