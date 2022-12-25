package com.tyin.core.repository.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.core.module.bean.RegionLabel;
import com.tyin.core.module.entity.AdminRegion;
import com.tyin.core.module.res.admin.AdminRegionRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/22 13:45
 * @description ...
 */
public interface AdminRegionRepository extends BaseMapper<AdminRegion> {

    @Select("""
            SELECT
            \t`id`,
            \t`parent_id`,
            \t`id` `value`,
            \t`full_name` `label`
            FROM \t`admin_region` ${ew.customSqlSegment}""")
    List<RegionLabel> selectLabel(@Param("ew") LambdaQueryWrapper<AdminRegion> wrapper);

    @Select(value = """
            SELECT
            \t`parent_id` pid,
            \t`id`,
            \t`name`,
            \t`full_name`,
            \t`lat`,
            \t`lng`,
            \t`level`,
            \t(SELECT count(id) FROM `admin_region` WHERE `parent_id` = `region`.`id` limit 1) > 0 hasChildren
            FROM admin_region AS `region`
            WHERE ${ew.sqlSegment}
            """)
    List<AdminRegionRes> selectResList(@Param("ew") LambdaQueryWrapper<AdminRegion> wrapper);
}
