package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;

import java.util.HashSet;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
public interface IAdminMenuService {

    HashSet<String> getMenuPermission(AdminUser adminUser);

    List<AdminMenu> getRouterByPermission(Long userId);

    MenuLabelRes getMenuLabel(Integer id);

    List<MenuRes.MenuItem> getMenuRes(String keywords, Integer roleId, Boolean disabled);
}
