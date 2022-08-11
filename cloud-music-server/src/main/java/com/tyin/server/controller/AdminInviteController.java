package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.res.admin.AdminInviteCodeRes;
import com.tyin.core.module.res.admin.GenerateInviteRes;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.IdValid;
import com.tyin.server.service.IAdminInviteCodeService;
import com.tyin.server.service.IAdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Tyin
 * @date 2022/7/28 17:18
 * @description ...
 */
@Api(tags = "邀请码管理-相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/invite")
@RequiredArgsConstructor
public class AdminInviteController {
    private final IAdminUserService userService;

    private final IAdminInviteCodeService adminInviteCodeService;

    @ApiOperation("邀请码查询列表")
    @GetMapping("/list")
    public Result<PageResult<AdminInviteCodeRes, ?>> getInviteCodeList(@ApiParam("使用者") @RequestParam(required = false, defaultValue = "") String useBy,
                                                                       @ApiParam("创建者") @RequestParam(required = false, defaultValue = "") String createBy,
                                                                       @ApiParam("是否有效") @RequestParam(required = false) Boolean invalid,
                                                                       @ApiParam("是否使用") @RequestParam(required = false) Boolean used,
                                                                       @ApiParam(value = "页长度", defaultValue = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                                       @ApiParam(value = "当前页", defaultValue = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                                       @ApiParam(hidden = true) @Auth("@permission.hasPermission('sys:account:invite')") AuthAdminUser ignoredUser) {
        PageResult<AdminInviteCodeRes, ?> resPageResult = adminInviteCodeService.getList(useBy, createBy, invalid, used, current, size);
        return Result.success(resPageResult);
    }

    @ApiOperation("生成邀请码")
    @PutMapping("/generate")
    public Result<?> generateInviteCode(@ApiParam("角色ID") @Valid @RequestBody IdValid valid, @Auth("@permission.hasPermission('sys:account:invite')") AuthAdminUser user) {
        InviteCodeBean code = userService.generateInviteCode(valid.getId(), user);
        GenerateInviteRes build = GenerateInviteRes.builder()
                .code(code.getCode())
                .time(code.getExpiration() / 1000 / 60 + "")
                .message(String.format("已生成邀请码：%s，有效期 %s 分钟", code.getCode(), code.getExpiration() / 1000 / 60))
                .build();
        return Result.success(build);
    }

    @ApiOperation("清除邀请码")
    @PutMapping("/remove")
    public Result<?> removeInviteCode(@ApiParam("邀请码ID") @Valid @RequestBody IdValid valid, @Auth AuthAdminUser ignoredUser) {
        Integer row = adminInviteCodeService.remove(valid.getId());
        Asserts.isTrue(row == 1, "清理失败");
        return Result.success();
    }
}
