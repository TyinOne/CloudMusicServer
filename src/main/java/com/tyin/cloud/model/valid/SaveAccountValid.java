package com.tyin.cloud.model.valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Tyin
 * @date 2022/5/11 16:49
 * @description ...
 */

@Data
public class SaveAccountValid {
    @NotNull
    @NotBlank
    @ApiModelProperty("用户名")
    private String account;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("邮箱")
    private String mail;
    @ApiModelProperty("号码")
    private String phone;
    @ApiModelProperty("地区编号")
    private String region;
    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    @ApiModelProperty("角色ID")
    private Long roleId;
    @ApiModelProperty("头像")
    private AvatarUpdate avatar;

    @Data
    public static class AvatarUpdate {
        @ApiModelProperty("头像URI")
        private String uri;
        @ApiModelProperty("头像文件名")
        private String fileName;
    }
}
