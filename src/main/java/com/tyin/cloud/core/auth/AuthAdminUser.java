package com.tyin.cloud.core.auth;

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
    private Long roleId;
    @ApiParam(hidden = true)
    private String role;
    @ApiParam(hidden = true)
    private Set<String> permissions;
}
