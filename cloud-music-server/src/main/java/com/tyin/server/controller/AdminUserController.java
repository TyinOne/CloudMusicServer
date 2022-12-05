package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.server.api.Result;
import com.tyin.server.auth.security.service.UserDetailsServiceImpl;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.params.valid.AdminLoginValid;
import com.tyin.server.params.valid.AdminRegisterValid;
import com.tyin.server.params.valid.sequence.AdminUserLoginValidSequence;
import com.tyin.server.params.valid.sequence.AdminUserRegisterValidSequence;
import com.tyin.server.service.IAdminUserService;
import com.tyin.server.utils.IpUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tyin
 * @date 2022/3/30 22:20
 * @description ...
 */
@Tag(name = "鉴权管理-用户鉴权接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final IAdminUserService adminUserService;
    private final UserDetailsServiceImpl userDetailsService;
    private final PropertiesComponents propertiesComponents;

    @PostMapping("/register")
    @Operation(description = "用户注册")
    public Result<?> register(@RequestBody @Validated(AdminUserRegisterValidSequence.class) AdminRegisterValid adminRegisterValid) {
        adminUserService.register(adminRegisterValid);
        return Result.success();
    }

    @PostMapping("/login")
    @Operation(description = "用户登录",tags = "用户登录")
    public Result<AdminUserLoginRes> login(@RequestBody @Validated(AdminUserLoginValidSequence.class) AdminLoginValid adminLoginValid, HttpServletRequest httpServletRequest) {
        //登录IP
        Long ipAddress = IpUtils.getIpAddressInt(httpServletRequest);
        adminLoginValid.setIpAddress(ipAddress);
        AdminUserLoginRes res = userDetailsService.login(adminLoginValid);
        String key = res.getUuid();
        //更新用户表的token
        adminUserService.updateToken(adminLoginValid.getAccount(), key);
        res.setAvatar(propertiesComponents.getOssUrl() + res.getAvatar());
        return Result.success(res);
    }


    @PutMapping("/logout")
    @Operation(description = "用户登出")
    public Result<?> logout(@Parameter(hidden = true) @Auth AuthAdminUser user) {
        adminUserService.logout(user);
        return Result.success();
    }

    @GetMapping("/info")
    @Operation(description = "获取用户信息")
    public Result<AdminUserDetailRes> getUserInfo(@Parameter(hidden = true) @Auth AuthAdminUser user) {
        AdminUserDetailRes res = adminUserService.getUserInfo(user);
        res.setAvatar(propertiesComponents.getOssUrl() + res.getAvatar());
        return Result.success(res);
    }

    @GetMapping("/session")
    @Operation(description = "用户状态认证")
    public Result<AdminUserLoginRes> getSession(@Parameter(hidden = true) @Auth AuthAdminUser user) {
        AdminUserLoginRes res = adminUserService.getUserSession(user);
        res.setAvatar(propertiesComponents.getOssUrl() + res.getAvatar());
        return Result.success(res);
    }
}
