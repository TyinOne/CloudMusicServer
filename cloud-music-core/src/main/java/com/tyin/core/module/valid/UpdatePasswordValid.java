package com.tyin.core.module.valid;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/11/21 1:25
 * @description ...
 */
@Data
public class UpdatePasswordValid {
    private String account;
    private String password;
}
