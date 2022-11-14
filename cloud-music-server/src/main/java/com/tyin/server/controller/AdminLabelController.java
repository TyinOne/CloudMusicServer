package com.tyin.server.controller;


import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.bean.RegionLabel;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.bean.ScheduledGroupLabel;
import com.tyin.core.module.res.admin.*;
import com.tyin.server.api.Result;
import com.tyin.server.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Open-各种Options-Label接口")
@RestController
@RequestMapping("${cloud.api.prefix.admin}/label")
@RequiredArgsConstructor
public class AdminLabelController {
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;
    private final IAdminRegionService adminRegionService;
    private final IAdminDictService adminDictService;
    private final IAdminScheduledService adminScheduledService;

    @GetMapping("/region")
    @Operation(description = "区域树形数据接口")
    public Result<RegionLabelRes> getRegionLabel(@Parameter(description = "根级", example = "0") @RequestParam(required = false, defaultValue = "0") Long rootId) {
        List<RegionLabel> list = adminRegionService.getRegionLabel(rootId);
        return Result.success(RegionLabelRes.builder().list(list).build());
    }

    @GetMapping("/role")
    @Operation(description = "角色列表Label接口")
    public Result<RoleLabelRes> getRoleLabel() {
        List<RoleLabel> list = adminRoleService.getRoleLabel();
        return Result.success(RoleLabelRes.builder().list(list).build());
    }

    @GetMapping("/role/key")
    @Operation(description = "角色列表Label接口")
    public Result<RoleLabelRes> getRoleKeyLabel() {
        List<RoleLabel> list = adminRoleService.getRoleKeyLabel();
        return Result.success(RoleLabelRes.builder().list(list).build());
    }

    @GetMapping("/menu")
    @Operation(description = "菜单树形Label数据接口(勾选)")
    public Result<MenuLabelRes> getMenuLabel(@RequestParam(required = false) Integer id) {
        MenuLabelRes res = adminMenuService.getMenuLabel(id);
        return Result.success(res);
    }

    @GetMapping("/menu/select")
    @Operation(description = "菜单树形Label数据接口(选择)")
    public Result<MenuTreeSelectLabelRes> getMenuTreeSelectLabel() {
        MenuTreeSelectLabelRes res = adminMenuService.getMenuTreeSelectLabel();
        return Result.success(res);
    }

    @GetMapping("/dict")
    @Operation(description = "字典Options接口)")
    public Result<DictLabelRes> getDictLabel() {
        List<DictLabel> list = adminDictService.getDictLabel();
        return Result.success(DictLabelRes.builder().list(list).build());
    }

    @GetMapping("/sched_group")
    @Operation(description = "任务调度分组Options接口")
    public Result<ScheduledGroupLabelRes> getScheduledGroupSelect() {
        List<ScheduledGroupLabel> list = adminScheduledService.getGroupLabel();
        return Result.success(ScheduledGroupLabelRes.builder().list(list).build());
    }
}
