package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.IdValid;
import com.tyin.server.params.valid.SaveAccountValid;
import com.tyin.server.service.IAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Tyin
 * @date 2022/5/5 20:25
 * @description ...
 */
@Api(tags = "用户管理-相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final IAdminUserService userService;

    @ApiOperation("用户列表")
    @GetMapping("/list")
    public Result<PageResult<AdminAccountRes, ?>> getUserList(@ApiParam("用户 昵称/号码关键词") @RequestParam(required = false) String name,
                                                              @ApiParam("角色ID") @RequestParam(required = false, defaultValue = "0") Long roleId,
                                                              @ApiParam("是否禁用") @RequestParam(required = false, defaultValue = "-1") Long disabled,
                                                              @ApiParam(value = "页长度", defaultValue = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                              @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                              @ApiParam(hidden = true) @Auth("@permission.hasPermission('sys:account:query')") AuthAdminUser user) {
        PageResult<AdminAccountRes, ?> res = userService.getUserList(size, current, name, roleId, disabled);
        return Result.success(res);
    }

    @ApiOperation("用户详情")
    @GetMapping("/detail")
    public Result<AdminAccountDetailRes> getUserDetail(@ApiParam("用户名") @RequestParam String account, @Auth("@permission.hasPermission('sys:account:detail')") AuthAdminUser user) {
        AdminAccountDetailRes res = userService.getAccountDetail(account);
        return Result.success(res);
    }

    @ApiOperation("保存用户信息")
    @PostMapping("/save")
    public Result<?> saveAccountInfo(@Validated @RequestBody SaveAccountValid valid, @Auth("@permission.hasPermission('sys:account:save')") AuthAdminUser user) {
        userService.saveAccountInfo(valid);
        return Result.success();
    }

    @ApiOperation("生成邀请码")
    @PutMapping("/generate/invite")
    public Result<?> generateInviteCode(@ApiParam("角色ID") @Valid IdValid valid, @Auth("@permission.hasPermission('sys:account:invite')") AuthAdminUser user) {
        InviteCodeBean code = userService.generateInviteCode(valid.getId(), user);
        return Result.success(String.format("已生成邀请码：%s，有效期 %s 分钟", code.getCode(), code.getExpiration() / 1000 / 60));

    }

}
