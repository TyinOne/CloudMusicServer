package com.tyin.cloud.controller.admin;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.base.TreeBase;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.service.admin.IAdminMenuService;
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
public class AdminMenuController {
    private final IAdminMenuService adminMenuService;

    @GetMapping("/list/tree")
    public Result<MenuRes> getMenuRes(@RequestParam(required = false) String keywords, @Auth AuthAdminUser user) {
        List<? extends TreeBase> menuRes = adminMenuService.getMenuRes(keywords);
        return Result.success(MenuRes.builder().list(menuRes).build());
    }

    @GetMapping("/label")
    public Result<MenuLabelRes> getMenuLabel(@RequestParam(required = false) Integer id, @Auth AuthAdminUser user) {
        MenuLabelRes res = adminMenuService.getMenuLabel(id);
        return Result.success(res);
    }
}
