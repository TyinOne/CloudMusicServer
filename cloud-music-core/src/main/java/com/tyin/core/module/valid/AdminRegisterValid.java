package com.tyin.core.module.valid;

import com.tyin.core.module.valid.sequence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/7/27 9:40
 * @description ...
 */
@Data
public class AdminRegisterValid implements Serializable {
    @Schema(description = "用户名/邮箱/号码")
    @Length(min = 6, max = 12, message = "请输入6 ~ 12位用户名", groups = UsernameLengthCheck.class)
    @NotBlank(message = "请输入用户名", groups = UsernameBankCheck.class)
    private String account;

    @Schema(description = "密码")
    @Length(min = 8, max = 12, message = "请输入8 ~ 12位密码", groups = PasswordLengthCheck.class)
    @NotBlank(message = "请输入密码", groups = PasswordBankCheck.class)
    private String password;

    @Schema(description = "注册密码")
    @Length(min = 6, max = 6, message = "请输入正确邀请码, 联系管理员获取", groups = InviteCheck.class)
    private String code;
}
