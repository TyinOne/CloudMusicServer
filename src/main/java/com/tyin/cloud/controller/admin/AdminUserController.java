package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.utils.IpUtils;
import com.tyin.cloud.model.entity.AdminUserDetailRes;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.valid.sequence.AdminUserLoginValidSequence;
import com.tyin.cloud.service.admin.IAdminUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/login")
    public Result<AdminUserLoginRes> login(@Validated(AdminUserLoginValidSequence.class) @RequestBody AdminLoginParams adminLoginParams, HttpServletRequest httpServletRequest) {
        //登录IP
        Long ipAddress = IpUtils.getIpAddressInt(httpServletRequest);
        AdminUserLoginRes res = adminUserService.login(adminLoginParams, ipAddress);
        return Result.success(res);
    }

    @PutMapping("/logout")
    public Result<?> logout(@Auth AuthAdminUser user) {
        adminUserService.logout(user);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<AdminUserDetailRes> getUserInfo(@Auth AuthAdminUser user) {
        AdminUserDetailRes res = adminUserService.getUserInfo(user);
        return Result.success(res);
    }

    @GetMapping("/session")
    public Result<AdminUserLoginRes> getSession(@Auth AuthAdminUser user) {
        AdminUserLoginRes res = adminUserService.getUserSession(user);
        return Result.success(res);
    }
}
