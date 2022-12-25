package com.tyin.core.repository.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.core.module.bean.MenuLabel;
import com.tyin.core.module.bean.MenuTreeSelectLabel;
import com.tyin.core.module.entity.AdminMenu;
import com.tyin.core.module.res.admin.MenuDetailRes;
import com.tyin.core.module.res.admin.MenuRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/9 23:50
 * @description ...
 */
public interface AdminMenuRepository extends BaseMapper<AdminMenu> {
    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`meta_title`,
            \t`sort`
            FROM
            \t`admin_menu` ${ew.customSqlSegment}""")
    List<MenuLabel> selectLabel(@Param("ew") LambdaQueryWrapper<AdminMenu> wrapper);

    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`name`,
            \t`path`,
            \t`component`,
            \t`meta_icons`,
            \t`meta_title`,
            \t`meta_roles`,
            \t`security`,
            \t`type`,
            \t`sort`
            FROM
            \t`admin_menu` ${ew.customSqlSegment}""")
    List<MenuRes.MenuItem> selectListRes(@Param("ew") LambdaQueryWrapper<AdminMenu> eq);

    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`meta_title`,
            \t`type`
            FROM
            \t`admin_menu`WHERE deleted = 0 and disabled = 0 and type in (0, 1)""")
    List<MenuTreeSelectLabel> selectTreeSelectLabel();

    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`sort`,
            \t`type`,
            \t`security`,
            \t`name`,
            \t`path`,
            \t`redirect`,
            \t`component`,
            \t`meta_title`,
            \t`meta_icons`,
            \t`meta_is_link`,
            \t`meta_roles`,
            \t`meta_is_hide`,
            \t`meta_is_affix`,
            \t`meta_is_iframe`,
            \t`meta_is_keep_alive`,
            \t`disabled`\s
            FROM
            \t`admin_menu`\s
            WHERE
            \t`deleted` = 0 and `id` = #{id}""")
    MenuDetailRes selectDetailById(@Param("id") Integer id);

    @Select("""
            SELECT
             *
            FROM
             admin_menu
            WHERE
             type = 2
             AND FIND_IN_SET(id,(SELECT menu_id FROM admin_role_menu WHERE role_key = #{role_key}))""")
    List<AdminMenu> selectButtonSecurityByRole(@Param("role_key") String roleValue);

    @Select("""
            SELECT `value` FROM `admin_role`  WHERE id IN(SELECT role_id FROM `admin_role_menu` WHERE (INSTR(`menu_id`, #{id}) > 0 OR INSTR(`half_id`, #{id}) > 0 ))
            """)
    List<String> selectRoleByMenuId(@Param("id") Long id);
}
