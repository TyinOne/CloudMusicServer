package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.core.utils.TreeUtils;
import com.tyin.cloud.model.bean.MenuLabel;
import com.tyin.cloud.model.bean.MenuTreeSelectLabel;
import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminRoleMenu;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.MenuDetailRes;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.model.res.MenuTreeSelectLabelRes;
import com.tyin.cloud.model.valid.SaveMenuValid;
import com.tyin.cloud.repository.admin.AdminMenuRepository;
import com.tyin.cloud.repository.admin.AdminRoleMenuRepository;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.tyin.cloud.core.constants.PermissionConstants.SUPPER_ROLE;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ROLE_MENU_PREFIX;

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
    private final AdminRoleMenuRepository adminRoleMenuRepository;
    private final RedisComponents redisComponents;

    @Override
    public HashSet<String> getMenuPermission(AdminUser adminUser) {
        return Sets.newHashSet();
    }

    @Override
    public List<AdminMenu> getRouterByPermission(Long userId) {
        List<AdminMenu> adminMenusForUser = Lists.newArrayList();
        AdminRole role = adminRoleService.getRoles(userId);
        if (role.getValue().equals(SUPPER_ROLE)) {
            String key = ROLE_MENU_PREFIX + SUPPER_ROLE;
            String menuArrayStr = redisComponents.get(key);
            if (StringUtils.isNotEmpty(menuArrayStr)) {
                List<AdminMenu> adminMenusForRole = JsonUtils.toJavaObjectList(menuArrayStr, AdminMenu.class);
                if (Objects.nonNull(adminMenusForRole)) adminMenusForUser.addAll(adminMenusForRole);
            } else {
                List<AdminMenu> adminMenus = adminMenuRepository.selectList(Wrappers.<AdminMenu>lambdaQuery().gt(AdminMenu::getId, 1).ne(AdminMenu::getType, 2));
                adminMenusForUser.addAll(adminMenus);
                redisComponents.save(key, JsonUtils.toJSONString(adminMenus));
            }
        } else {
            String key = ROLE_MENU_PREFIX + role.getValue();
            String menuArrayStr = redisComponents.get(key);
            Set<String> ids = Sets.newHashSet();
            if (StringUtils.isNotEmpty(menuArrayStr)) {
                List<AdminMenu> adminMenusForRole = JsonUtils.toJavaObjectList(menuArrayStr, AdminMenu.class);
                if (Objects.nonNull(adminMenusForRole)) adminMenusForUser.addAll(adminMenusForRole);
            } else {
                List<AdminRoleMenu> adminRoleMenus = adminRoleMenuRepository.selectList(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, role.getId()));
                adminRoleMenus.forEach(item -> {
                    ids.addAll(Sets.newHashSet(item.getMenuId().split(",")));
                    ids.addAll(Sets.newHashSet(item.getHalfId().split(",")));
                });
                if (adminRoleMenus.size() > 0) {
                    List<AdminMenu> adminMenus = adminMenuRepository.selectList(Wrappers.<AdminMenu>lambdaQuery().in(AdminMenu::getId, ids).ne(AdminMenu::getType, 2));
                    adminMenusForUser.addAll(adminMenus);
                }
                redisComponents.save(key, JsonUtils.toJSONString(adminMenusForUser));
            }
        }
        adminMenusForUser.add(adminMenuRepository.selectById(1L));
        return adminMenusForUser.stream().sorted(Comparator.comparingLong(AdminMenu::getId)).collect(Collectors.toList());
    }

    @Override
    public MenuLabelRes getMenuLabel(Integer rowId) {
        List<Long> selected = Lists.newArrayList();
        if (Objects.nonNull(rowId)) {
            selected = adminRoleService.getRoleMenuSelectedLabel(rowId);
        }
        List<MenuLabel> list = adminMenuRepository.selectLabel(Wrappers.<AdminMenu>lambdaQuery().eq(AdminMenu::getDisabled, Boolean.FALSE));
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

    @Override
    public MenuTreeSelectLabelRes getMenuTreeSelectLabel() {
        List<MenuTreeSelectLabel> list = adminMenuRepository.selectTreeSelectLabel();
        return MenuTreeSelectLabelRes.builder()
                .list(TreeUtils.buildTree(list, 0L, Boolean.FALSE))
                .build();
    }

    @Override
    public MenuDetailRes getMenuDetailRes(Integer id) {
        return adminMenuRepository.selectDetailById(id);
    }

    @Override
    public void saveMenu(SaveMenuValid valid) {
        Long id = valid.getId();
        if (Objects.isNull(id)) {
            adminMenuRepository.insert(AdminMenu.builder()
                    .parentId(valid.getParentId())
                    .component(valid.getComponent())
                    .name(valid.getName())
                    .path(valid.getPath())
                    .redirect(valid.getRedirect())
                    .type(valid.getType())
                    .sort(valid.getSort())
                    .security(valid.getSecurity())
                    .metaTitle(valid.getMetaTitle())
                    .metaIcons(valid.getMetaIcons())
                    .metaIsAffix(valid.getMetaIsAffix())
                    .metaIsHide(valid.getMetaIsHide())
                    .metaIsIframe(valid.getMetaIsIframe())
                    .metaIsKeepAlive(valid.getMetaIsKeepAlive())
                    .disabled(valid.getDisabled())
                    .build());
        } else {
            adminMenuRepository.updateById(AdminMenu.builder()
                    .id(valid.getId())
                    .parentId(valid.getParentId())
                    .component(valid.getComponent())
                    .name(valid.getName())
                    .path(valid.getPath())
                    .redirect(valid.getRedirect())
                    .type(valid.getType())
                    .sort(valid.getSort())
                    .security(valid.getSecurity())
                    .metaTitle(valid.getMetaTitle())
                    .metaIcons(valid.getMetaIcons())
                    .metaIsAffix(valid.getMetaIsAffix())
                    .metaIsHide(valid.getMetaIsHide())
                    .metaIsIframe(valid.getMetaIsIframe())
                    .metaIsKeepAlive(valid.getMetaIsKeepAlive())
                    .disabled(valid.getDisabled())
                    .build());
        }
    }
}
