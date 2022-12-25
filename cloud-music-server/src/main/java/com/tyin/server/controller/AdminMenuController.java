package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.api.Result;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.MenuDetailRes;
import com.tyin.core.module.res.admin.MenuRes;
import com.tyin.core.module.valid.SaveMenuValid;
import com.tyin.core.service.IAdminMenuService;
import com.tyin.core.utils.Asserts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/9 23:38
 * @description ...
 */
@Tag(name = "菜单管理-菜单相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/menu")
@RequiredArgsConstructor
public class AdminMenuController {
    private final IAdminMenuService adminMenuService;

    @GetMapping("/list/tree")
    @Operation(summary = "菜单列表树形数据接口")
    public Result<MenuRes> getMenuRes(@Parameter(description = "关键词") @RequestParam(required = false) String keywords,
                                      @Parameter(description = "角色ID") @RequestParam(required = false) Integer roleId,
                                      @Parameter(description = "是否禁用") @RequestParam(required = false) Boolean disabled,
                                      @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:menu:query')") AuthAdminUser ignoredUser) {
        List<MenuRes.MenuItem> menuRes = adminMenuService.getMenuRes(keywords, roleId, disabled);
        return Result.success(MenuRes.builder().list(menuRes).build());
    }

    @GetMapping("/detail")
    @Operation(summary = "菜单详情")
    public Result<MenuDetailRes> getDetailRes(@RequestParam Integer id, @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:menu:detail')") AuthAdminUser ignoredUser) {
        MenuDetailRes res = adminMenuService.getMenuDetailRes(id);
        return Result.success(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存目录/菜单/按钮信息")
    public Result<?> saveMenu(@RequestBody SaveMenuValid valid, @Parameter(hidden = true) @Auth("@permission.hasPermission('sys:menu:save')") AuthAdminUser ignoredUser) {
        Asserts.isTrue(adminMenuService.saveMenu(valid) > 0, "保存失败");
        return Result.success();
    }
}
