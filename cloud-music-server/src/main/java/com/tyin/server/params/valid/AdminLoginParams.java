package com.tyin.server.params.valid;

import com.tyin.server.params.valid.sequence.PasswordCheck;
import com.tyin.server.params.valid.sequence.UsernameCheck;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("用户名/邮箱/号码")
    @NotBlank(message = "请输入用户名", groups = UsernameCheck.class)
    private String account;
    @ApiModelProperty("密码")
    @NotBlank(message = "请输入密码", groups = PasswordCheck.class)
    private String password;
}