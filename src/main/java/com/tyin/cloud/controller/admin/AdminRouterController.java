package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminRouterListRes;
import com.tyin.cloud.service.admin.IAdminRouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyin
 * @date 2022/5/5 16:21
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/router")
@RequiredArgsConstructor
public class AdminRouterController {

    private final IAdminRouterService routerService;

    @GetMapping("/permission")
    public Result<AdminRouterListRes> getRouterByPermission(@Auth AuthAdminUser user) {
        AdminRouterListRes res = routerService.getRouterByPermission(user);
        return Result.success(res);
    }
}
