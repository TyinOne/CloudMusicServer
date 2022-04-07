package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.repository.admin.AdminRoleRepository;
import com.tyin.cloud.service.admin.IAdminRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 0:44
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements IAdminRoleService {
    private final AdminRoleRepository adminRoleRepository;

    @Override
    public List<AdminRole> getRoles(AdminUser adminUser) {
        return adminRoleRepository.selectList(Wrappers.<AdminRole>lambdaQuery()
                .apply("id IN ( SELECT role_id FROM `admin_user_role` WHERE `user_id` = {0} ) ", adminUser.getId())
                .eq(AdminRole::getDisabled, Boolean.FALSE)
        );
    }
}
