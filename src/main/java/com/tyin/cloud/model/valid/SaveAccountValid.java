package com.tyin.cloud.model.valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private String account;
    private String nickName;
    private String mail;
    private String phone;
    private String region;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birth;
    private Long roleId;
    private AvatarUpdate avatar;

    @Data
    public static class AvatarUpdate {
        private String uri;
        private String fileName;
    }
}
