package com.tyin.server.controller;


import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.AdminRoleRes;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.PageResult;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.InsertRoleValid;
import com.tyin.server.params.valid.UpdateRoleValid;
import com.tyin.server.service.IAdminRoleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.tyin.core.constants.ResMessageConstants.ADD_FAILED;

/**
 * @author Tyin
 * @date 2022/4/9 4:22
 * @description ...
 */
@Tag(name = "角色管理-角色相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/role")
@RequiredArgsConstructor
public class AdminRoleController {
    private final IAdminRoleService adminRoleService;

    @GetMapping("/list")
    public Result<PageResult<AdminRoleRes, ?>> getRoleList(@RequestParam(required = false) String keywords,
                                                           @RequestParam(required = false, defaultValue = "20") Long size,
                                                           @RequestParam(required = false, defaultValue = "1") Long current,
                                                           @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:role:query')") AuthAdminUser ignoredUser) {
        PageResult<AdminRoleRes, ?> pageResult = adminRoleService.getRolesPageResult(keywords, size, current);
        return Result.success(pageResult);
    }

    @PostMapping("/add")
    public Result<?> addRole(@RequestBody InsertRoleValid valid, @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:role:add')") AuthAdminUser user) {
        Integer row = adminRoleService.addRole(valid, user);
        Asserts.isTrue(row == 1, ADD_FAILED);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateRole(@RequestBody UpdateRoleValid valid, @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:role:update')") AuthAdminUser user) {
        adminRoleService.updateRole(valid, user);
        return Result.success();
    }


}
