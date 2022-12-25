package com.tyin.core.repository.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.entity.AdminUserRole;
import com.tyin.core.module.res.admin.AdminRoleRes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/8 0:39
 * @description ...
 */
public interface AdminRoleRepository extends BaseMapper<AdminRole> {

    /**
     * 后台角色查询分页
     *
     * @param page    分页page
     * @param wrapper wrapper
     * @return 分页数据
     */
    @Select("""
            SELECT
            \t`id`,
            \t`name`,
            \t`value`,
            \t`description`,
            \t`disabled`,
            \t`sort`,
            \t`created`\s
            FROM
            \t`admin_role` ${ew.customSqlSegment}\s
            """)
    IPage<AdminRoleRes> selectPageRes(Page<AdminRole> page, @Param("ew") LambdaQueryWrapper<AdminRole> wrapper);

    @Select("""
            SELECT
            \t `id` as value,
            \t `name` as label
            FROM
            \t `admin_role` ${ew.customSqlSegment}\s
            """)
    List<RoleLabel> selectLabel(@Param("ew") LambdaQueryWrapper<AdminRole> lambdaQuery);

    @Update("""
            INSERT INTO `admin_user_role`(user_id, role_id, created, modified) VALUES (
            (SELECT `id` FROM `admin_user` WHERE `account` = #{account}),(SELECT admin_role.id FROM admin_role WHERE admin_role.value = #{roleValue}),sysdate(),sysdate());
            """)
    Integer addUserRole(@Param("account") String account, @Param("roleValue") String roleValue);

    @Insert("""
            INSERT INTO
             admin_user_role
             (user_id, role_id, deleted, created, modified)
            VALUES
             (#{adminUserRole.userId}, #{adminUserRole.roleId}, 0, sysdate(), sysdate())
            """)
    Integer insertUserRole(@Param("adminUserRole") AdminUserRole adminUserRole);

    @Select("""
            SELECT value FROM admin_role WHERE id IN(SELECT role_id FROM admin_user_role WHERE user_id = #{id} AND admin_user_role.deleted = 0)
            """)
    Set<String> selectRolesByUserId(@Param("id") Long id);

    @Select("""
            SELECT
             `value` as value,
             `name` as label
            FROM
             `admin_role` ${ew.customSqlSegment}\s
            """)
    List<RoleLabel> selectKeyLabel(@Param("ew") LambdaQueryWrapper<AdminRole> eq);

    @Update("""
            UPDATE `admin_user_role` SET deleted = 1 WHERE user_id = #{id}
            """)
    Integer removeAllRoleByUserId(@Param("id") Long id);
}
