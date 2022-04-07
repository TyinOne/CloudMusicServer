package com.tyin.cloud.service.admin.impl;

import com.google.common.collect.Sets;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.AdminUserPermissionRes.RouterRes;
import com.tyin.cloud.service.admin.IAdminMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 1:04
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminMenuServiceImpl implements IAdminMenuService {
    @Override
    public HashSet<String> getMenuPermission(AdminUser adminUser) {
        return Sets.newHashSet();
    }

    @Override
    public List<RouterRes> getRouterForUser(Long id) {
        return null;
    }
}
