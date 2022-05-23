package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.MenuDetailRes;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.model.valid.SaveMenuValid;
import com.tyin.cloud.service.admin.IAdminMenuService;
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
                                      @Auth("@permission.hasPermission('sys:menu:query')") AuthAdminUser user) {
        List<MenuRes.MenuItem> menuRes = adminMenuService.getMenuRes(keywords, roleId, disabled);
        return Result.success(MenuRes.builder().list(menuRes).build());
    }

    @GetMapping("/detail")
    @ApiOperation("菜单详情")
    public Result<MenuDetailRes> getDetailRes(@RequestParam Integer id, @Auth("@permission.hasPermission('sys:menu:detail')") AuthAdminUser user) {
        MenuDetailRes res = adminMenuService.getMenuDetailRes(id);
        return Result.success(res);
    }
    @PostMapping("/save")
    @ApiOperation("保存目录/菜单/按钮信息")
    public Result<?> saveMenu(@RequestBody SaveMenuValid valid, @Auth("@permission.hasPermission('sys:menu:save')") AuthAdminUser user) {
        adminMenuService.saveMenu(valid);
        return Result.success();
    }
}
