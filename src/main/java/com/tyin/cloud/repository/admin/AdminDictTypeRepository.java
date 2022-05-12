package com.tyin.cloud.repository.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.cloud.model.bean.DictLabel;
import com.tyin.cloud.model.entity.AdminDictType;
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
}
