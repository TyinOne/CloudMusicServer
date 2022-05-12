package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.constants.ResMessageConstants;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.StringUtils;
import com.tyin.cloud.model.bean.RoleLabel;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminRoleMenu;
import com.tyin.cloud.model.res.AdminRoleRes;
import com.tyin.cloud.model.valid.InsertRoleValid;
import com.tyin.cloud.model.valid.UpdateRoleValid;
import com.tyin.cloud.repository.admin.AdminRoleMenuRepository;
import com.tyin.cloud.repository.admin.AdminRoleRepository;
import com.tyin.cloud.service.admin.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tyin.cloud.core.constants.RedisKeyConstants.ROLE_MENU_PREFIX;
import static com.tyin.cloud.core.constants.ResMessageConstants.ROLE_HAS_EXIST;

/**
 * @author Tyin
 * @date 2022/4/8 0:44
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements IAdminRoleService {
    private final AdminRoleRepository adminRoleRepository;
    private final AdminRoleMenuRepository adminRoleMenuRepository;
    private final RedisComponents redisComponents;

    @Override
    public AdminRole getRoles(Long userId) {
        return adminRoleRepository.selectOne(Wrappers.<AdminRole>lambdaQuery()
                .apply("`id` IN ( SELECT `role_id` FROM `admin_user_role` WHERE `user_id` = {0} ) ", userId)
                .eq(AdminRole::getDisabled, Boolean.FALSE)
        );
    }

    @Override
    public AdminRole getRoleById(Long roleId) {
        return adminRoleRepository.selectById(roleId);
    }

    @Override
    public PageResult<AdminRoleRes, ?> getRolesPageResult(String keywords, Long size, Long current) {
        IPage<AdminRoleRes> resPage = adminRoleRepository.selectPageRes(new Page<>(current, size),
                Wrappers.<AdminRole>lambdaQuery()
                        .apply(StringUtils.isNotEmpty(keywords), " INSTR(`name`, {0}) > 0", keywords)
                        .orderByAsc(AdminRole::getSort)
        );
        return PageResult.buildResult(resPage);
    }

    @Override
    public Integer addRole(InsertRoleValid valid, AuthAdminUser user) {
        String value = valid.getValue();
        String name = valid.getName();
        Asserts.isTrue(adminRoleRepository.selectCount(Wrappers.<AdminRole>lambdaQuery().or().eq(AdminRole::getValue, value).or().eq(AdminRole::getName, name)) == 0, ROLE_HAS_EXIST);
        return adminRoleRepository.insert(AdminRole.builder()
                .name(valid.getName())
                .value(valid.getValue())
                .description(valid.getDescription())
                .disabled(valid.getDisabled())
                .sort(valid.getSort())
                .build());
    }

    @Override
    public void updateRole(UpdateRoleValid valid, AuthAdminUser user) {
        String value = valid.getValue();
        String name = valid.getName();
        Long id = valid.getId();
        AdminRole adminRole = adminRoleRepository.selectOne(Wrappers.<AdminRole>lambdaQuery().ne(AdminRole::getId, id).and(i -> i.eq(AdminRole::getValue, value).or().eq(AdminRole::getName, name)));
        Asserts.isTrue(Objects.nonNull(adminRole), ROLE_HAS_EXIST);
        AdminRoleMenu adminRoleMenu = adminRoleMenuRepository.selectOne(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, id));
        int roleMenuRow = Objects.nonNull(adminRoleMenu) ?
                adminRoleMenuRepository.updateById(
                        AdminRoleMenu.builder()
                                .id(adminRoleMenu.getId())
                                .roleId(id)
                                .menuId(String.join(",", valid.getMenu()))
                                .halfId(String.join(",", valid.getHalf()))
                                .build()
                )
                :
                adminRoleMenuRepository.insert(
                        AdminRoleMenu.builder()
                                .roleId(id)
                                .menuId(String.join(",", valid.getMenu()))
                                .halfId(String.join(",", valid.getHalf()))
                                .build()
                );

        int roleRow = adminRoleRepository.updateById(AdminRole.builder()
                .id(valid.getId())
                .name(valid.getName())
                .value(valid.getValue())
                .description(valid.getDescription())
                .disabled(valid.getDisabled())
                .sort(valid.getSort())
                .build());
        redisComponents.deleteKey(ROLE_MENU_PREFIX + adminRole.getValue());
        Asserts.isTrue(roleMenuRow == 1 && roleRow == 1, ResMessageConstants.UPDATE_FAILED);
    }

    @Override
    public List<Long> getRoleMenuSelectedLabel(Integer rowId) {
        AdminRoleMenu adminRoleMenu = adminRoleMenuRepository.selectOne(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, rowId));
        if (Objects.isNull(adminRoleMenu) || StringUtils.isEmpty(adminRoleMenu.getMenuId()))
            return Lists.newArrayList();
        return Arrays.stream(adminRoleMenu.getMenuId().split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    @Override
    public List<Long> getRoleMenuHalfLabel(Integer rowId) {
        AdminRoleMenu adminRoleMenu = adminRoleMenuRepository.selectOne(Wrappers.<AdminRoleMenu>lambdaQuery().eq(AdminRoleMenu::getRoleId, rowId));
        if (Objects.isNull(adminRoleMenu) || StringUtils.isEmpty(adminRoleMenu.getHalfId()))
            return Lists.newArrayList();
        return Arrays.stream(adminRoleMenu.getHalfId().split(",")).map(Long::parseLong).collect(Collectors.toList());
    }

    @Override
    public List<RoleLabel> getRoleLabel() {
        return adminRoleRepository.selectLabel(Wrappers.<AdminRole>lambdaQuery().eq(AdminRole::getDisabled, Boolean.FALSE));
    }

    @Override
    public List<RoleLabel> getRoleLabel(Set<Long> ids) {
        return adminRoleRepository.selectLabel(
                Wrappers.<AdminRole>lambdaQuery()
                        .eq(AdminRole::getDisabled, Boolean.FALSE)
                        .in(AdminRole::getId, ids)
        );
    }

    @Override
    public void updateUserRole(String account, Long roleId) {
        adminRoleRepository.updateUserRole(account, roleId);
    }
}
