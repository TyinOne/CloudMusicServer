package com.tyin.core.module.valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tyin.core.module.valid.sequence.PasswordBankCheck;
import com.tyin.core.module.valid.sequence.PasswordLengthCheck;
import com.tyin.core.module.valid.sequence.UsernameBankCheck;
import com.tyin.core.module.valid.sequence.UsernameLengthCheck;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/3/31 9:50
 * @description ...
 */
@Data
public class AdminLoginValid implements Serializable {

    @Schema(description = "用户名/邮箱/手机号码")
    @NotBlank(message = "请输入用户名", groups = UsernameBankCheck.class)
    @Length(min = 6, max = 12, message = "请输入6 ~ 12位用户名", groups = UsernameLengthCheck.class)
    private String account;

    @Schema(description = "密码")
    @NotBlank(message = "请输入密码", groups = PasswordBankCheck.class)
    @Length(min = 32, max = 32, message = "请输入8 ~ 12位密码", groups = PasswordLengthCheck.class)
    private String password;

    @Parameter(hidden = true)
    @JsonIgnore
    private Long ipAddress;
}
