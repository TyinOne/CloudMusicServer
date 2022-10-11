package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.SaveAccountValid;
import com.tyin.server.service.IAdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tyin
 * @date 2022/5/5 20:25
 * @description ...
 */
@Tag(name = "用户管理-相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final IAdminUserService userService;

    @Operation(description = "用户列表")
    @GetMapping("/list")
    public Result<PageResult<AdminAccountRes, ?>> getUserList(@Parameter(description = "用户 昵称/号码关键词") @RequestParam(required = false) String name,
                                                              @Parameter(description = "角色ID") @RequestParam(required = false, defaultValue = "0") Long roleId,
                                                              @Parameter(description = "是否禁用") @RequestParam(required = false, defaultValue = "-1") Long disabled,
                                                              @Parameter(description = "页长度", example = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                              @Parameter(description = "当前页", example = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                              @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:account:query')") AuthAdminUser ignoredUser) {
        PageResult<AdminAccountRes, ?> res = userService.getUserList(size, current, name, roleId, disabled);
        return Result.success(res);
    }

    @Operation(description = "用户详情")
    @GetMapping("/detail")
    public Result<AdminAccountDetailRes> getUserDetail(@Parameter(description = "用户名") @RequestParam String account, @Auth("@permission.hasPermission('sys:account:detail')") AuthAdminUser ignoredUser) {
        AdminAccountDetailRes res = userService.getAccountDetail(account);
        return Result.success(res);
    }

    @Operation(description = "保存用户信息")
    @PostMapping("/save")
    public Result<?> saveAccountInfo(@Validated @RequestBody SaveAccountValid valid, @Auth("@permission.hasPermission('sys:account:save')") AuthAdminUser ignoredUser) {
        userService.saveAccountInfo(valid);
        return Result.success();
    }
}
