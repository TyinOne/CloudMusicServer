package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.core.utils.TreeUtils;
import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.AdminUserPermissionRes.RouterRes;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuLabelRes.MenuData;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.repository.admin.AdminMenuRepository;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements IAdminMenuService {
    private final IAdminRoleService adminRoleService;
    private final AdminMenuRepository adminMenuRepository;

    @Override
    public HashSet<String> getMenuPermission(AdminUser adminUser) {
        return Sets.newHashSet();
    }

    @Override
    public List<RouterRes> getRouterForUser(Long id) {
        return null;
    }

    @Override
    public MenuLabelRes getMenuLabel(Integer rowId) {
        List<Long> selected = Lists.newArrayList();
        if (Objects.nonNull(rowId)) {
            selected = adminRoleService.getRoleMenuSelectedLabel(rowId);
        }
        List<MenuData> list = adminMenuRepository.selectLabel(Wrappers.<AdminMenu>lambdaQuery().eq(AdminMenu::getDisabled, Boolean.FALSE));
        return MenuLabelRes.builder()
                .list(TreeUtils.buildTree(list, 0L, Boolean.TRUE))
                .selected(selected)
                .build();
    }

    @Override
    public List<MenuRes.MenuItem> getMenuRes(String keywords, Integer roleId, Boolean disabled) {
        List<Long> ids = Lists.newArrayList();
        if (roleId > 1) {
            ids = adminRoleService.getRoleMenuSelectedLabel(roleId);
            ids.addAll(adminRoleService.getRoleMenuHalfLabel(roleId));
        }
        List<MenuRes.MenuItem> res = adminMenuRepository.selectListRes(Wrappers.<AdminMenu>lambdaQuery()
                .apply(StringUtils.isNotEmpty(keywords), "INSTR(`meta_title`, {0}) > 0", keywords)
                .in(roleId > 1, AdminMenu::getId, ids)
                .eq(Objects.nonNull(disabled), AdminMenu::getDisabled, disabled)
        );
        return TreeUtils.buildTree(res, 0L, Boolean.TRUE);
    }
}
