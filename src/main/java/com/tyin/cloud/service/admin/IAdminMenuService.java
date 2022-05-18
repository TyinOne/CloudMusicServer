package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.res.MenuDetailRes;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;
import com.tyin.cloud.model.res.MenuTreeSelectLabelRes;
import com.tyin.cloud.model.valid.SaveMenuValid;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
public interface IAdminMenuService {

    Set<String> getButtonPermission(Long roleId);

    List<AdminMenu> getRouterByPermission(Long userId);

    MenuLabelRes getMenuLabel(Integer id);

    List<MenuRes.MenuItem> getMenuRes(String keywords, Integer roleId, Boolean disabled);

    MenuTreeSelectLabelRes getMenuTreeSelectLabel();

    MenuDetailRes getMenuDetailRes(Integer id);

    void saveMenu(SaveMenuValid valid);
}
