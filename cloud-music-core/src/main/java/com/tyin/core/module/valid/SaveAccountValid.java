package com.tyin.core.module.valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/5/11 16:49
 * @description ...
 */

@Data
public class SaveAccountValid {
    @NotNull
    @NotBlank
    @Schema(description = "用户名")
    private String account;
    @Schema(description = "昵称")
    private String nickName;
    @Schema(description = "邮箱")
    private String mail;
    @Schema(description = "号码")
    private String phone;
    @Schema(description = "地区编号")
    private String region;
    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    @Schema(description = "角色ID")
    private Set<String> roles;
    @Schema(description = "头像")
    private AvatarUpdate avatar;

    @Data
    public static class AvatarUpdate {
        @Schema(description = "头像URI")
        private String uri;
        @Schema(description = "头像文件名")
        private String fileName;
    }
}
