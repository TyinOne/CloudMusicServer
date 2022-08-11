package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.annotations.Open;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.AdminLoginValid;
import com.tyin.server.params.valid.AdminRegisterValid;
import com.tyin.server.params.valid.sequence.AdminUserLoginValidSequence;
import com.tyin.server.params.valid.sequence.AdminUserRegisterValidSequence;
import com.tyin.server.service.IAdminUserService;
import com.tyin.server.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Tyin
 * @date 2022/3/30 22:20
 * @description ...
 */
@Api(tags = "鉴权管理-用户鉴权接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/user")
@RequiredArgsConstructor
public class AdminUserController {
    private final IAdminUserService adminUserService;

    @Open
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Result<?> register(@RequestBody @Validated(AdminUserRegisterValidSequence.class) AdminRegisterValid adminRegisterValid) {
        adminUserService.register(adminRegisterValid);
        return Result.success();
    }

    @Open
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<AdminUserLoginRes> login(@RequestBody @Validated(AdminUserLoginValidSequence.class) AdminLoginValid adminLoginValid, HttpServletRequest httpServletRequest) {
        //登录IP
        Long ipAddress = IpUtils.getIpAddressInt(httpServletRequest);
        AdminUserLoginRes res = adminUserService.login(adminLoginValid, ipAddress);
        return Result.success(res);
    }

    @PutMapping("/logout")
    @ApiOperation("用户登出")
    public Result<?> logout(@Auth @ApiIgnore AuthAdminUser user) {
        adminUserService.logout(user);
        return Result.success();
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result<AdminUserDetailRes> getUserInfo(@Auth @ApiIgnore AuthAdminUser user) {
        AdminUserDetailRes res = adminUserService.getUserInfo(user);
        return Result.success(res);
    }

    @GetMapping("/session")
    @ApiOperation("用户状态认证")
    public Result<AdminUserLoginRes> getSession(@Auth @ApiIgnore AuthAdminUser user) {
        AdminUserLoginRes res = adminUserService.getUserSession(user);
        return Result.success(res);
    }
}
