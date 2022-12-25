package com.tyin.core.repository.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.module.res.admin.AdminInviteCodeRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tyin
 * @date 2022/7/22 18:02
 * @description ...
 */
public interface AdminInviteCodeRepository extends BaseMapper<AdminInviteCode> {

    @Select("""
            SELECT
                `id`,
                `code`,
                (SELECT `name` FROM `admin_role` WHERE `role_id` = `admin_role`.`id`) AS `role_name`,
                `invalid`,
                `used`,
                `create_by`,
                `created`,
                `use_by`,
                `expiration_time`
            FROM `admin_invite_code` ${ew.customSqlSegment}
            """)
    IPage<AdminInviteCodeRes> selectResList(Page<AdminInviteCode> adminInviteCodePage, @Param("ew") Wrapper<AdminInviteCode> wrapper);
}
