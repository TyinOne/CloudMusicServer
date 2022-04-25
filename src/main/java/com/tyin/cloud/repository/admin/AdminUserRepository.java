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
            \t`user`.`account`,
            \t`user`.`nick_name`,
            \tCONCAT(#{oss},`user`.`avatar`) `avatar`,
            \t`user`.`phone`,
            \t`user`.`mail`,
            \t(SELECT GROUP_CONCAT( `name` ) FROM `admin_role` WHERE `id` IN ( SELECT role_id FROM admin_user_role WHERE user_id = `user`.id )) `role`,
            \t`extra`.`sex`,
            \t`extra`.`birth`,
            \t`extra`.`region`,
            \t`extra`.`id_card_no`,
            \t`extra`.`id_card_name`,
            \t`extra`.`id_card_address`
            FROM
            \t`admin_user` `user`
            LEFT JOIN `admin_user_extra` `extra` on `extra`.`user_id` = `user`.`id`
            WHERE
            \t`account` = #{account}
            """)
    AdminUserDetailRes selectUserDetail(@Param("account") String account, @Param("oss") String ossUrl);
}
