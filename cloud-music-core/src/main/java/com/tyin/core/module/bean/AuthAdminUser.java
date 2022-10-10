package com.tyin.core.module.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author Tyin
 * @date 2022/3/26 3:16
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthAdminUser {
    @ApiParam(hidden = true)
    private Long id;
    @ApiParam(hidden = true)
    private String token;
    @ApiParam(hidden = true)
    private String nickName;
    @ApiParam(hidden = true)
    private String account;
    @ApiParam(hidden = true)
    private String avatar;
    @ApiParam(hidden = true)
    private Set<String> roles;
    @ApiParam(hidden = true)
    private Set<String> permissions;

    @JsonIgnore
    @ApiParam(hidden = true)
    private Boolean disabled;

    @JsonIgnore
    @ApiParam(hidden = true)
    private String password;
}
