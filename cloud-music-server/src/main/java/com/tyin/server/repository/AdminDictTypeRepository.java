package com.tyin.server.repository;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.bean.DictLabel;
import com.tyin.core.module.entity.AdminDictType;
import com.tyin.core.module.res.admin.AdminDictTypeRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/12 0:27
 * @description ...
 */
public interface AdminDictTypeRepository extends BaseMapper<AdminDictType> {
    @Select("""
            SELECT
            \t`dict_type` value,
            \t`dict_label` label\s
            FROM
            \t`admin_dict_type`\s
            WHERE deleted = 0
            """)
    List<DictLabel> selectDictLabel();

    @Select("SELECT `dict_label` FROM `admin_dict_type` WHERE `deleted` = 0 AND `dict_type` = #{type}")
    String selectLabelByType(@Param("type") String dictType);

    @Select("SELECT id, dict_label, dict_type, dict_description, deleted, created FROM admin_dict_type ${ew.customSqlSegment}")
    IPage<AdminDictTypeRes> selectTypeListPages(@Param("page") Page<Object> page, @Param("ew") LambdaQueryWrapper<AdminDictType> wrapper);
}
