package com.tyin.cloud.model.params;

import com.tyin.cloud.model.valid.sequence.PasswordCheck;
import com.tyin.cloud.model.valid.sequence.UsernameCheck;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/3/31 9:50
 * @description ...
 */
@Data
public class AdminLoginParams implements Serializable {

    @NotBlank(message = "请输入用户名", groups = UsernameCheck.class)
    private String account;
    @NotBlank(message = "请输入密码", groups = PasswordCheck.class)
    private String password;
}
