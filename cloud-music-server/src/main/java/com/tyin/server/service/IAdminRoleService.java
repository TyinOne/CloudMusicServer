package com.tyin.server.service;


import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.res.admin.AdminRoleRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.params.valid.InsertRoleValid;
import com.tyin.server.params.valid.UpdateRoleValid;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/8 0:44
 * @description ...
 */
public interface IAdminRoleService {
    List<AdminRole> getRoles(Long id);

    AdminRole getRoleById(Long roleId);

    PageResult<AdminRoleRes, ?> getRolesPageResult(String keywords, Long size, Long current);

    Integer addRole(InsertRoleValid valid, AuthAdminUser user);

    Integer addUserRole(Long userId, Long roleId);

    void updateRole(UpdateRoleValid valid, AuthAdminUser user);

    List<Long> getRoleMenuSelectedLabel(Integer rowId);

    List<Long> getRoleMenuHalfLabel(Integer rowId);

    List<RoleLabel> getRoleLabel();

    List<RoleLabel> getRoleLabel(Set<Long> ids);

    void updateUserRole(String account, Long roleId);

    AdminRole selectById(Long roleId);
}
