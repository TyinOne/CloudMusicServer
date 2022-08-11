package com.tyin.server.params.valid;

import com.tyin.server.params.valid.sequence.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/7/27 9:40
 * @description ...
 */
@Data
public class AdminRegisterValid implements Serializable {
    @ApiModelProperty("用户名/邮箱/号码")
    @Length(min = 6, max = 12, message = "请输入6 ~ 12位用户名", groups = UsernameLengthCheck.class)
    @NotBlank(message = "请输入用户名", groups = UsernameBankCheck.class)
    private String account;

    @ApiModelProperty("密码")
    @Length(min = 8, max = 12, message = "请输入8 ~ 12位密码", groups = PasswordLengthCheck.class)
    @NotBlank(message = "请输入密码", groups = PasswordBankCheck.class)
    private String password;

    @ApiModelProperty("注册密码")
    @Length(min = 6, max = 6, message = "请输入正确邀请码, 联系管理员获取", groups = InviteCheck.class)
    private String code;
}
