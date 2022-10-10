package com.tyin.server.service;


import com.tyin.core.module.entity.AdminMenu;
import com.tyin.core.module.res.admin.MenuDetailRes;
import com.tyin.core.module.res.admin.MenuLabelRes;
import com.tyin.core.module.res.admin.MenuRes;
import com.tyin.core.module.res.admin.MenuTreeSelectLabelRes;
import com.tyin.server.params.valid.SaveMenuValid;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
public interface IAdminMenuService {

    Set<String> getButtonPermission(String roleKey);

    List<AdminMenu> getRouterByPermission(Long userId);

    MenuLabelRes getMenuLabel(Integer id);

    List<MenuRes.MenuItem> getMenuRes(String keywords, Integer roleId, Boolean disabled);

    MenuTreeSelectLabelRes getMenuTreeSelectLabel();

    MenuDetailRes getMenuDetailRes(Integer id);

    Integer saveMenu(SaveMenuValid valid);
}
