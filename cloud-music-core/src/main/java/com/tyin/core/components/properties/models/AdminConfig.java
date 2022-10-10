package com.tyin.core.components.properties.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/7/27 14:39
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminConfig {

    @JsonProperty("default_avatar")
    private String defaultAvatar;

    @JsonProperty("invite_code_expiration")
    private Integer inviteCodeExpiration;
}
