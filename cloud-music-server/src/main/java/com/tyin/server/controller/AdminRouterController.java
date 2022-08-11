package com.tyin.server.controller;


import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminRouterListRes;
import com.tyin.server.api.Result;
import com.tyin.server.service.IAdminRouterService;
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

    @GetMapping("/getRouter")
    public Result<AdminRouterListRes> getRouterByPermission(@Auth AuthAdminUser user) {
        AdminRouterListRes res = routerService.getRouterByPermission(user);
        return Result.success(res);
    }
}
