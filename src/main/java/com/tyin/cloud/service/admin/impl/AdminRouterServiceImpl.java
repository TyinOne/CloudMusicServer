package com.tyin.cloud.service.admin.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.TreeUtils;
import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.AdminRouterListRes;
import com.tyin.cloud.model.res.AdminRouterListRes.AdminRouterRes;
import com.tyin.cloud.model.res.AdminRouterListRes.AdminRouterRes.MetaRes;
import com.tyin.cloud.service.admin.IAdminMenuService;
import com.tyin.cloud.service.admin.IAdminRouterService;
import com.tyin.cloud.service.admin.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.tyin.cloud.core.constants.ResMessageConstants.USER_NOT_FOUND;

/**
 * @author Tyin
 * @date 2022/5/5 16:28
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminRouterServiceImpl implements IAdminRouterService {
    private final IAdminMenuService adminMenuService;
    private final IAdminUserService adminUserService;

    @Override
    public AdminRouterListRes getRouterByPermission(AuthAdminUser user) {
        List<AdminRouterRes> resList = Lists.newArrayList();
        AdminUser userEntity = adminUserService.getUserEntity(user.getAccount());
        Asserts.isTrue(Objects.nonNull(userEntity), USER_NOT_FOUND);
        List<AdminMenu> routerRes = adminMenuService.getRouterByPermission(userEntity.getId());
        routerRes.forEach(i -> {
            AdminRouterRes build = AdminRouterRes.builder()
                    .meta(MetaRes.builder()
                            .isAffix(i.getMetaIsAffix())
                            .isHide(i.getMetaIsHide())
                            .isIframe(i.getMetaIsIframe())
                            .isKeepAlive(i.getMetaIsKeepAlive())
                            .isLink(i.getMetaIsLink())
                            .roles(Sets.newHashSet(i.getMetaRoles().split(",")))
                            .title(i.getMetaTitle())
                            .build())
                    .name(i.getName())
                    .component(i.getComponent())
                    .path(i.getPath())
                    .redirect(i.getRedirect())
                    .build();
            build.setId(i.getId());
            build.setParentId(i.getParentId());
            resList.add(build);
        });

        return AdminRouterListRes.builder().list(TreeUtils.buildTree(resList, 0L, Boolean.FALSE)).build();
    }
}
