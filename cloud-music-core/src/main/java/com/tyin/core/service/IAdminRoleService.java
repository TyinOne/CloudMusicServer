package com.tyin.core.service;


import com.tyin.core.api.PageResult;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.res.admin.AdminRoleRes;
import com.tyin.core.module.valid.InsertRoleValid;
import com.tyin.core.module.valid.UpdateRoleValid;

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

    List<RoleLabel> getRoleKeyLabel();

    List<RoleLabel> getRoleLabel(Set<Long> ids);

    Integer addUserRoleKey(String account, String roleValue);

    AdminRole selectById(Long roleId);

    Integer removeAllRoleByUserId(Long id);
}
