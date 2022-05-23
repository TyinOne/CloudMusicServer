package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.model.res.AdminRoleRes;
import com.tyin.cloud.model.valid.InsertRoleValid;
import com.tyin.cloud.model.valid.UpdateRoleValid;
import com.tyin.cloud.service.admin.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.tyin.cloud.core.constants.ResMessageConstants.ADD_FAILED;

/**
 * @author Tyin
 * @date 2022/4/9 4:22
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/role")
@RequiredArgsConstructor
public class AdminRoleController {
    private final IAdminRoleService adminRoleService;

    @GetMapping("/list")
    public Result<PageResult<AdminRoleRes, ?>> getRoleList(@RequestParam(required = false) String keywords,
                                                           @RequestParam(required = false, defaultValue = "20") Long size,
                                                           @RequestParam(required = false, defaultValue = "1") Long current,
                                                           @Auth("@permission.hasPermission('sys:role:query')") AuthAdminUser user) {
        PageResult<AdminRoleRes, ?> pageResult = adminRoleService.getRolesPageResult(keywords, size, current);
        return Result.success(pageResult);
    }

    @PostMapping("/add")
    public Result<?> addRole(@RequestBody InsertRoleValid valid, @Auth("@permission.hasPermission('sys:role:add')") AuthAdminUser user) {
        Integer row = adminRoleService.addRole(valid, user);
        Asserts.isTrue(row == 1, ADD_FAILED);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<?> updateRole(@RequestBody UpdateRoleValid valid, @Auth("@permission.hasPermission('sys:role:update')") AuthAdminUser user) {
        adminRoleService.updateRole(valid, user);
        return Result.success();
    }


}
