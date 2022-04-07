package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminUser;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 0:44
 * @description ...
 */
public interface IAdminRoleService {
    List<AdminRole> getRoles(AdminUser adminUser);
}
