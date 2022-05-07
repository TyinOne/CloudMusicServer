package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.bean.RegionLabel;
import com.tyin.cloud.model.bean.RoleLabel;
import com.tyin.cloud.model.res.AdminRoleLabelRes;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.RegionLabelRes;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRegionService;
import com.tyin.cloud.service.admin.IAdminRoleService;
import io.swagger.v3.oas.annotations.Parameter;
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
@RestController
@RequestMapping("${cloud.api.prefix.admin}/label")
@RequiredArgsConstructor
public class AdminLabelController {
    private final IAdminRoleService adminRoleService;
    private final IAdminMenuService adminMenuService;
    private final IAdminRegionService adminRegionService;

    @GetMapping("/region")
    @Open
    public Result<RegionLabelRes> getRegionLabel(@RequestParam(required = false, defaultValue = "0") Long rootId) {
        List<RegionLabel> list = adminRegionService.getRegionLabel(rootId);
        return Result.success(RegionLabelRes.builder().list(list).build());
    }

    @GetMapping("/role")
    public Result<AdminRoleLabelRes> getRoleLabel(@Auth AuthAdminUser user) {
        List<RoleLabel> list = adminRoleService.getRoleLabel();
        return Result.success(AdminRoleLabelRes.builder().list(list).build());
    }

    @GetMapping("/menu")
    public Result<MenuLabelRes> getMenuLabel(@RequestParam(required = false) Integer id, @Parameter(hidden = true) @Auth AuthAdminUser user) {
        MenuLabelRes res = adminMenuService.getMenuLabel(id);
        return Result.success(res);
    }
}
