package com.tyin.core.repository.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.AdminVersion;
import com.tyin.core.module.res.admin.AdminVersionRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tyin
 * @date 2022/6/2 13:55
 * @description ...
 */
public interface AdminVersionMapper extends BaseMapper<AdminVersion> {
    /**
     * @param page               分页数据
     * @param lambdaQueryWrapper wrappers
     * @return iPage
     */
    @Select("""
            SELECT
            `id`,
            `version`,
            `src`,
            `release_time`,
            `forced`,
            `latest`,
            `hash`,
            `md5`,
            `update_log`
            FROM admin_version ${ew.customSqlSegment}
            """)
    IPage<AdminVersionRes> selectPageRes(@Param("page") Page<AdminVersionRes> page, @Param("ew") LambdaQueryWrapper<AdminVersion> lambdaQueryWrapper);
}
