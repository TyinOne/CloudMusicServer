package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.base.TreeBase;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.AdminUserPermissionRes;
import com.tyin.cloud.model.res.MenuLabelRes;

import java.util.HashSet;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
public interface IAdminMenuService {

    HashSet<String> getMenuPermission(AdminUser adminUser);

    List<AdminUserPermissionRes.RouterRes> getRouterForUser(Long id);

    MenuLabelRes getMenuLabel(Integer id);

    List<? extends TreeBase> getMenuRes(String keywords);
}
