package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.model.entity.AdminRole;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.res.AdminRoleLabelRes.RoleLabel;
import com.tyin.cloud.model.res.AdminRoleRes;
import com.tyin.cloud.model.valid.InsertRoleValid;
import com.tyin.cloud.model.valid.UpdateRoleValid;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/8 0:44
 * @description ...
 */
public interface IAdminRoleService {
    List<AdminRole> getRoles(AdminUser adminUser);

    PageResult<AdminRoleRes,?> getRolesPageResult(String keywords, Long size, Long current);

    Integer addRole(InsertRoleValid valid, AuthAdminUser user);

    void updateRole(UpdateRoleValid valid, AuthAdminUser user);

    List<Long> getRoleMenuSelectedLabel(Integer rowId);

    List<Long> getRoleMenuHalfLabel(Integer rowId);

    List<RoleLabel> getRoleLabel();
}
