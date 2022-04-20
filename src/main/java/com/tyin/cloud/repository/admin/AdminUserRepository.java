package com.tyin.cloud.repository.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyin.cloud.model.entity.AdminUser;
import com.tyin.cloud.model.entity.AdminUserDetailRes;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Tyin
 * @date 2022/3/30 10:00
 * @description ...
 */
public interface AdminUserRepository extends BaseMapper<AdminUser> {

    /**
     * 获取个人中心用户详情数据
     *
     * @param account 用户名
     * @param ossUrl  oss
     * @return 用户详情（个人中心）
     */
    @Select("""
            SELECT
            \t`account`,
            \t`name`,
            \tCONCAT(#{oss},`avatar`) `avatar`,
            \t`phone`,
            \t`mail`,
            \t(SELECT GROUP_CONCAT( `name` ) FROM `admin_role` WHERE `id` IN ( SELECT role_id FROM admin_user_role WHERE user_id = `user`.id )) `role`
            FROM
            \t`admin_user` `user`
            WHERE
            \t`account` = #{account}
            """)
    AdminUserDetailRes selectUserDetail(@Param("account") String account, @Param("oss") String ossUrl);
}
