package com.tyin.cloud.repository.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.cloud.model.entity.AdminMenu;
import com.tyin.cloud.model.res.MenuLabelRes;
import com.tyin.cloud.model.res.MenuRes;
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
    List<MenuLabelRes.MenuData> selectLabel(@Param("ew") LambdaQueryWrapper<AdminMenu> wrapper);

    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`name`,
            \t`path`,
            \t`component`,
            \t`meta_icons`,
            \t`meta_title`,
            \t`security`,
            \t`type`,
            \t`sort`
            FROM
            \t`admin_menu` ${ew.customSqlSegment}""")
    List<MenuRes.MenuItem> selectListRes(@Param("ew") LambdaQueryWrapper<AdminMenu> eq);
}
