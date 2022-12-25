package com.tyin.core.repository.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.module.res.admin.AdminScheduleRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tyin
 * @date 2022/7/14 10:49
 * @description ...
 */
public interface AdminScheduledRepository extends BaseMapper<AdminScheduled> {

    @Select("""
            SELECT
            `id`,
            `scheduled_name` AS `name`,
            `scheduled_group` AS `group`,
            `invoke_target` AS `method`,
            `cron_expression` AS `cron`,
            `disabled`,
            `created`
            FROM admin_scheduled
            ${ew.customSqlSegment}
            """)
    IPage<AdminScheduleRes> selectPageRes(@Param("ew") QueryWrapper<AdminScheduled> wrapper, Page<Object> objectPage);
}
