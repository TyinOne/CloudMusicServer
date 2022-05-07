package com.tyin.cloud.repository.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.cloud.model.bean.RegionLabel;
import com.tyin.cloud.model.entity.AdminRegion;
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
            FROM
            \t`admin_region` ${ew.customSqlSegment}""")
    List<RegionLabel> selectLabel(@Param("ew") LambdaQueryWrapper<AdminRegion> wrapper);
}
