package com.tyin.core.repository.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.AdminRequestLog;
import com.tyin.core.module.res.admin.AdminLogRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tyin
 * @date 2022/3/30 23:29
 * @description ...
 */
public interface AdminRequestLogRepository extends BaseMapper<AdminRequestLog> {
    @Select("""
            SELECT `id`, `uri`, `ip`, `method`, `status`, `request_method`, `elapsed`, `created`
            FROM `request_log`
            ${ew.customSqlSegment}
            GROUP BY `id`
            """)
    IPage<AdminLogRes> selectLogPage(Page<Object> page, @Param("ew") LambdaQueryWrapper<AdminRequestLog> wrappers);
}
