package com.tyin.core.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.AdminMenu;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.res.admin.AdminRouterListRes;
import com.tyin.core.service.IAdminMenuService;
import com.tyin.core.service.IAdminRouterService;
import com.tyin.core.service.IAdminUserService;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.tyin.core.constants.ResMessageConstants.USER_NOT_FOUND;

/**
 * @author Tyin
 * @date 2022/5/5 16:28
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminRouterServiceImpl implements IAdminRouterService {
    private final IAdminMenuService adminMenuService;
    private final IAdminUserService adminUserService;

    @Override
    public AdminRouterListRes getRouterByPermission(AuthAdminUser user) {
        List<AdminRouterListRes.AdminRouterRes> resList = Lists.newArrayList();
        AdminUser userEntity = adminUserService.getUserEntity(user.getAccount());
        Asserts.isTrue(Objects.nonNull(userEntity), USER_NOT_FOUND);
        List<AdminMenu> routerRes = adminMenuService.getRouterByPermission(userEntity.getId());
        routerRes.forEach(i -> {
            AdminRouterListRes.AdminRouterRes build = AdminRouterListRes.AdminRouterRes.builder()
                    .meta(AdminRouterListRes.AdminRouterRes.MetaRes.builder()
                            .isAffix(i.getMetaIsAffix())
                            .isHide(i.getMetaIsHide())
                            .isIframe(i.getMetaIsIframe())
                            .isKeepAlive(i.getMetaIsKeepAlive())
                            .isLink(i.getMetaIsLink())
                            .roles(Sets.newHashSet(i.getMetaRoles().split(",")))
                            .title(i.getMetaTitle())
                            .icon(i.getMetaIcons())
                            .build())
                    .name(i.getName())
                    .component(i.getComponent())
                    .path(i.getPath())
                    .redirect(i.getRedirect())
                    .build();
            build.setId(i.getId());
            build.setParentId(i.getParentId());
            build.setSort(i.getSort());
            resList.add(build);
        });

        return AdminRouterListRes.builder().list(TreeUtils.buildTree(resList, 0L, Boolean.TRUE)).build();
    }
}
