package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.AdminAccountDetailRes;
import com.tyin.cloud.model.res.AdminAccountRes;
import com.tyin.cloud.model.valid.SaveAccountValid;
import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Tyin
 * @date 2022/5/5 20:25
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final IAdminUserService userService;

    @GetMapping("/list")
    public Result<PageResult<AdminAccountRes, ?>> getUserList(@RequestParam(required = false) String name,
                                                              @RequestParam(required = false, defaultValue = "20") Long size,
                                                              @RequestParam(required = false, defaultValue = "1") Long current,
                                                              @RequestParam(required = false, defaultValue = "0") Long roleId,
                                                              @RequestParam(required = false, defaultValue = "-1") Long disabled,
                                                              @Auth("@permission.hasPermission('sys:account:query')") AuthAdminUser user) {
        PageResult<AdminAccountRes, ?> res = userService.getUserList(size, current, name, roleId, disabled);
        return Result.success(res);
    }

    @GetMapping("/detail")
    public Result<AdminAccountDetailRes> getUserDetail(@RequestParam String account, @Auth("@permission.hasPermission('sys:account:detail')") AuthAdminUser user) {
        AdminAccountDetailRes res = userService.getAccountDetail(account);
        return Result.success(res);
    }

    @PostMapping("/save")
    public Result<?> saveAccountInfo(@Validated @RequestBody SaveAccountValid valid, @Auth("@permission.hasPermission('sys:account:save')") AuthAdminUser user) {
        userService.saveAccountInfo(valid);
        return Result.success();
    }
}
