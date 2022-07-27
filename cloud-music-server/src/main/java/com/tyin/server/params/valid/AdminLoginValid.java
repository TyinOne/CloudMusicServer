package com.tyin.server.params.valid;

import com.tyin.server.params.valid.sequence.PasswordCheck;
import com.tyin.server.params.valid.sequence.UsernameCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/3/31 9:50
 * @description ...
 */
@Data
public class AdminLoginValid implements Serializable {

    @ApiModelProperty("用户名/邮箱/号码")
    @NotBlank(message = "请输入用户名", groups = UsernameCheck.class)
    @Length(min = 6, max = 12,message = "请输入6 ~ 12位用户名", groups = UsernameCheck.class)
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "请输入密码", groups = PasswordCheck.class)
    @Length(min = 8, max = 12,message = "请输入8 ~ 12位密码", groups = PasswordCheck.class)
    private String password;
}
