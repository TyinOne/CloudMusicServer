package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.service.admin.IAdminMenuService;
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
 * @date 2022/4/9 23:38
 * @description ...
 */
@RestController
@RequestMapping("${cloud.api.prefix.admin}/menu")
@RequiredArgsConstructor
@Tag(name = "Menu菜单")
public class AdminMenuController {
    private final IAdminMenuService adminMenuService;

    @GetMapping("/list/tree")
    @Operation(summary = "菜单树形结构")
    public Result<MenuRes> getMenuRes(@RequestParam(required = false) String keywords,
                                      @RequestParam(required = false) Integer roleId,
                                      @RequestParam(required = false) Boolean disabled,
                                      @Parameter(hidden = true) @Auth AuthAdminUser user) {
        List<MenuRes.MenuItem> menuRes = adminMenuService.getMenuRes(keywords, roleId, disabled);
        return Result.success(MenuRes.builder().list(menuRes).build());
    }

    @GetMapping("/label")
    public Result<MenuLabelRes> getMenuLabel(@RequestParam(required = false) Integer id, @Parameter(hidden = true) @Auth AuthAdminUser user) {
        MenuLabelRes res = adminMenuService.getMenuLabel(id);
        return Result.success(res);
    }
}
