package com.tyin.server.controller;


import com.tyin.core.annotations.Open;
import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.bean.RegionLabel;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.res.admin.*;
import com.tyin.server.api.Result;
import com.tyin.server.service.IAdminDictService;
import com.tyin.server.service.IAdminMenuService;
import com.tyin.server.service.IAdminRegionService;
import com.tyin.server.service.IAdminRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/7 13:40
 * @description ...
 */

@Api(tags = "Open-各种Options-Label接口")
@Open
@RestController
@RequestMapping("${cloud.api.prefix.admin}/label")
@RequiredArgsConstructor
public class AdminLabelController {
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;
    private final IAdminRegionService adminRegionService;
    private final IAdminDictService adminDictService;

    @GetMapping("/region")
    @ApiOperation("区域树形数据接口")
    public Result<RegionLabelRes> getRegionLabel(@ApiParam(value = "根级", defaultValue = "0s") @RequestParam(required = false, defaultValue = "0") Long rootId) {
        List<RegionLabel> list = adminRegionService.getRegionLabel(rootId);
        return Result.success(RegionLabelRes.builder().list(list).build());
    }

    @GetMapping("/role")
    @ApiOperation("角色列表Label接口")
    public Result<RoleLabelRes> getRoleLabel() {
        List<RoleLabel> list = adminRoleService.getRoleLabel();
        return Result.success(RoleLabelRes.builder().list(list).build());
    }

    @GetMapping("/menu")
    @ApiOperation("菜单树形Label数据接口(勾选)")
    public Result<MenuLabelRes> getMenuLabel(@RequestParam(required = false) Integer id) {
        MenuLabelRes res = adminMenuService.getMenuLabel(id);
        return Result.success(res);
    }

    @GetMapping("/menu/select")
    @ApiOperation("菜单树形Label数据接口(选择)")
    public Result<MenuTreeSelectLabelRes> getMenuTreeSelectLabel() {
        MenuTreeSelectLabelRes res = adminMenuService.getMenuTreeSelectLabel();
        return Result.success(res);
    }

    @GetMapping("/dict")
    @ApiOperation("字典Options接口)")
    public Result<DictLabelRes> getDictLabel() {
        List<DictLabel> list = adminDictService.getDictLabel();
        return Result.success(DictLabelRes.builder().list(list).build());
    }
}
