package com.tyin.server.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.bean.MenuLabel;
import com.tyin.core.module.bean.MenuTreeSelectLabel;
import com.tyin.core.module.entity.AdminMenu;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.entity.AdminRoleMenu;
import com.tyin.core.module.res.admin.MenuDetailRes;
import com.tyin.core.module.res.admin.MenuLabelRes;
import com.tyin.core.module.res.admin.MenuRes;
import com.tyin.core.module.res.admin.MenuTreeSelectLabelRes;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import com.tyin.core.utils.TreeUtils;
import com.tyin.server.params.valid.SaveMenuValid;
import com.tyin.server.repository.AdminMenuRepository;
import com.tyin.server.repository.AdminRoleMenuRepository;
import com.tyin.server.service.IAdminMenuService;
import com.tyin.server.service.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tyin.core.constants.PermissionConstants.SUPPER_ROLE;
import static com.tyin.core.constants.RedisKeyConstants.ROLE_MENU_PREFIX;
import static com.tyin.core.constants.RedisKeyConstants.SUPPER_MENU_PREFIX;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminMenuServiceImpl implements IAdminMenuService {
    private final IAdminRoleService adminRoleService;
    private final AdminMenuRepository adminMenuRepository;
    private final AdminRoleMenuRepository adminRoleMenuRepository;
    private final RedisComponents redisComponents;

    @Override
    public Set<String> getButtonPermission(Long roleId) {
        AdminRole role = adminRoleService.selectById(roleId);
        List<AdminMenu> menus = adminMenuRepository.selectButtonSecurityByRole(role.getId());
        return menus.stream().map(AdminMenu::getSecurity).collect(Collectors.toSet());
    }

    @Override
    public List<AdminMenu> getRouterByPermission(Long userId) {
        List<AdminMenu> adminMenusForUser = Lists.newArrayList();
        AdminRole role = adminRoleService.getRoles(userId);
        String allMenuKey = SUPPER_MENU_PREFIX + SUPPER_ROLE;
        String menuArrayStr = redisComponents.get(allMenuKey);
        List<AdminMenu> adminMenus;
        if (StringUtils.isEmpty(menuArrayStr)) {
            adminMenus = adminMenuRepository.selectList(Wrappers.<AdminMenu>lambdaQuery().gt(AdminMenu::getId, 1).ne(AdminMenu::getType, 2));
        } else {
            List<AdminMenu> jsonArray = JsonUtils.toJavaObjectList(menuArrayStr, AdminMenu.class);
            adminMenus = Objects.isNull(jsonArray) ? Lists.newArrayList() : jsonArray;
        }

        if (role.getValue().equals(SUPPER_ROLE)) {
            adminMenusForUser.addAll(adminMenus);
            redisComponents.saveAsync(allMenuKey, JsonUtils.toJSONString(adminMenus));
        } else {
            String key = ROLE_MENU_PREFIX + role.getValue();
            menuArrayStr = redisComponents.get(key);
            List<String> idArray = Objects.isNull(menuArrayStr) || StringUtils.isEmpty(menuArrayStr) ? Lists.newArrayList() : JsonUtils.toJavaObjectList(menuArrayStr, String.class);
            List<String> strings = Objects.isNull(idArray) || idArray.isEmpty() ? Lists.newArrayList() : idArray;
            if (StringUtils.isNotEmpty(menuArrayStr)) {
                adminMenus = adminMenus.stream().filter(i -> strings.contains(i.getId().toString())).collect(Collectors.toList());
                adminMenusForUser.addAll(adminMenus);
            } else {
                Set<String> ids = Sets.newHashSet();
                List<AdminRoleMenu> adminRoleMenus = adminRoleMenuRepository.selectList(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, role.getId()));
                adminRoleMenus.forEach(item -> {
                    ids.addAll(Sets.newHashSet(item.getMenuId().split(",")));
                    ids.addAll(Sets.newHashSet(item.getHalfId().split(",")));
                });
                if (adminRoleMenus.size() > 0) {
                    adminMenus = adminMenus.stream().filter(i -> ids.contains(i.getId().toString())).collect(Collectors.toList());
                    adminMenusForUser.addAll(adminMenus);
                }
                redisComponents.saveAsync(key, JsonUtils.toJSONString(ids));
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
    public Integer saveMenu(SaveMenuValid valid) {
        int row;
        Long id = valid.getId();
        if (Objects.isNull(id)) {
            AdminMenu build = AdminMenu.builder()
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
                    .metaIsLink(valid.getMetaIsLink())
                    .metaIsKeepAlive(valid.getMetaIsKeepAlive())
                    .disabled(valid.getDisabled())
                    .build();
            row = adminMenuRepository.insert(build);
        } else {
            row = adminMenuRepository.updateById(AdminMenu.builder()
                    .id(id)
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
                    .metaIsLink(valid.getMetaIsLink())
                    .metaIsKeepAlive(valid.getMetaIsKeepAlive())
                    .disabled(valid.getDisabled())
                    .build());
        }
        //更新Menu缓存
        redisComponents.save(SUPPER_MENU_PREFIX + SUPPER_ROLE, JsonUtils.toJSONString(adminMenuRepository.selectList(Wrappers.<AdminMenu>lambdaQuery().gt(AdminMenu::getId, 1).ne(AdminMenu::getType, 2))));
        return row;
    }
}
