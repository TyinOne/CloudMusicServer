package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.utils.IpUtils;
import com.tyin.cloud.model.params.AdminLoginParams;
import com.tyin.cloud.model.res.AdminUserLoginRes;
import com.tyin.cloud.model.res.AdminUserPermissionRes;
import com.tyin.cloud.model.valid.AdminUserLoginValidSequence;
import com.tyin.cloud.service.admin.IAdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tyin
 * @date 2022/3/30 22:20
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final IAdminUserService adminUserService;

    @Open
    @PostMapping("/login")
    public Result<AdminUserLoginRes> login(@Validated(AdminUserLoginValidSequence.class) @RequestBody AdminLoginParams adminLoginParams, HttpServletRequest httpServletRequest) {
        //登录IP
        Integer ipAddress = IpUtils.getIpAddressInt(httpServletRequest);
        AdminUserLoginRes res = adminUserService.login(adminLoginParams, ipAddress);
        return Result.success(res);
    }

    @GetMapping("/permission")
    public Result<AdminUserPermissionRes> getAdminUserPermission(@Auth AuthAdminUser user) {
        return Result.success();
    }
}
