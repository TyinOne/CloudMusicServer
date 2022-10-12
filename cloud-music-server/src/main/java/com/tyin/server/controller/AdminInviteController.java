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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Tyin
 * @date 2022/7/28 17:18
 * @description ...
 */
@Tag(name = "邀请码管理-相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/invite")
@RequiredArgsConstructor
public class AdminInviteController {
    private final IAdminUserService userService;

    private final IAdminInviteCodeService adminInviteCodeService;

    @Operation(description = "邀请码查询列表")
    @GetMapping("/list")
    public Result<PageResult<AdminInviteCodeRes, ?>> getInviteCodeList(@Parameter(description = "使用者") @RequestParam(required = false, defaultValue = "") String useBy,
                                                                       @Parameter(description = "创建者") @RequestParam(required = false, defaultValue = "") String createBy,
                                                                       @Parameter(description = "是否有效") @RequestParam(required = false) Boolean invalid,
                                                                       @Parameter(description = "是否使用") @RequestParam(required = false) Boolean used,
                                                                       @Parameter(description = "页长度", example = "20") @RequestParam(required = false, defaultValue = "20") Long size,
                                                                       @Parameter(description = "当前页", example = "1") @RequestParam(required = false, defaultValue = "1") Long current,
                                                                       @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:account:invite')") AuthAdminUser ignoredUser) {
        PageResult<AdminInviteCodeRes, ?> resPageResult = adminInviteCodeService.getList(useBy, createBy, invalid, used, current, size);
        return Result.success(resPageResult);
    }

    @Operation(description = "生成邀请码")
    @PutMapping("/generate")
    public Result<?> generateInviteCode(@Parameter(description = "角色ID") @Valid @RequestBody IdValid valid, @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:account:invite')") AuthAdminUser user) {
        InviteCodeBean code = userService.generateInviteCode(valid.getId(), user);
        GenerateInviteRes build = GenerateInviteRes.builder()
                .code(code.getCode())
                .time(code.getExpiration() / 1000 / 60 + "")
                .message(String.format("已生成邀请码：%s，有效期 %s 分钟", code.getCode(), code.getExpiration() / 1000 / 60))
                .build();
        return Result.success(build);
    }

    @Operation(description = "清除邀请码")
    @PutMapping("/remove")
    public Result<?> removeInviteCode(@Parameter(description = "邀请码ID") @Valid @RequestBody IdValid valid, @Parameter(hidden = true) @Auth AuthAdminUser ignoredUser) {
        Integer row = adminInviteCodeService.remove(valid.getId());
        Asserts.isTrue(row == 1, "清理失败");
        return Result.success();
    }
}
