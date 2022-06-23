package com.tyin.server.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.bean.RoleLabel;
import com.tyin.core.module.entity.AdminRole;
import com.tyin.core.module.res.admin.AdminRoleRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
            UPDATE `admin_user_role`
            \t SET `role_id` = #{roleId}
            WHERE `user_id` = (SELECT `id` FROM `admin_user` WHERE `account` = #{account})
            """)
    void updateUserRole(@Param("account") String account, @Param("roleId") Long roleId);
}