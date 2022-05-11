package com.tyin.cloud.repository.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.cloud.model.bean.DictLabel;
import com.tyin.cloud.model.entity.AdminDict;
import com.tyin.cloud.model.res.AdminDictRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/8 22:44
 * @description ...
 */
public interface AdminDictRepository extends BaseMapper<AdminDict> {

    @Select("""
            SELECT
            \t`dict_type` value,
            \t`dict_label` label\s
            FROM
            \t`admin_dict`\s
            WHERE deleted = 0
            GROUP BY
            \t`dict_type`,
            \t`dict_label`""")
    List<DictLabel> selectDictLabel();

    @Select("""
            SELECT
            \tid,
            \tdict_key,
            \tdict_type,
            \tdict_label,
            \tdict_value,
            \tdict_description\s
            FROM
            \t`admin_dict` ${ew.customSqlSegment}""")
    IPage<AdminDictRes> selectListPages(Page<AdminDict> page, @Param("ew") LambdaQueryWrapper<AdminDict> wrapper);
}
