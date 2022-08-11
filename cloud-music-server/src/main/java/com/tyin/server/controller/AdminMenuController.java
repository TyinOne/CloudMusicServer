package com.tyin.server.controller;

import com.tyin.core.annotations.Auth;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.res.admin.MenuDetailRes;
import com.tyin.core.module.res.admin.MenuRes;
import com.tyin.core.utils.Asserts;
import com.tyin.server.api.Result;
import com.tyin.server.params.valid.SaveMenuValid;
import com.tyin.server.service.IAdminMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/9 23:38
 * @description ...
 */
@Api(tags = "菜单管理-菜单相关接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/menu")
@RequiredArgsConstructor
public class AdminMenuController {
    private final IAdminMenuService adminMenuService;

    @GetMapping("/list/tree")
    @ApiOperation("菜单列表树形数据接口")
    public Result<MenuRes> getMenuRes(@ApiParam("关键词") @RequestParam(required = false) String keywords,
                                      @ApiParam("角色ID") @RequestParam(required = false) Integer roleId,
                                      @ApiParam("是否禁用") @RequestParam(required = false) Boolean disabled,
                                      @Auth("@permission.hasPermission('sys:menu:query')") AuthAdminUser ignoredUser) {
        List<MenuRes.MenuItem> menuRes = adminMenuService.getMenuRes(keywords, roleId, disabled);
        return Result.success(MenuRes.builder().list(menuRes).build());
    }

    @GetMapping("/detail")
    @ApiOperation("菜单详情")
    public Result<MenuDetailRes> getDetailRes(@RequestParam Integer id, @Auth("@permission.hasPermission('sys:menu:detail')") AuthAdminUser ignoredUser) {
        MenuDetailRes res = adminMenuService.getMenuDetailRes(id);
        return Result.success(res);
    }

    @PostMapping("/save")
    @ApiOperation("保存目录/菜单/按钮信息")
    public Result<?> saveMenu(@RequestBody SaveMenuValid valid, @Auth("@permission.hasPermission('sys:menu:save')") AuthAdminUser ignoredUser) {
        Asserts.isTrue(adminMenuService.saveMenu(valid) > 0, "保存失败");
        return Result.success();
    }
}
