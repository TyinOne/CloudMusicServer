package com.tyin.server.repository;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.AdminUser;
import com.tyin.core.module.entity.AdminUserDetailRes;
import com.tyin.core.module.res.admin.AdminAccountDetailRes;
import com.tyin.core.module.res.admin.AdminAccountRes;
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

    @Select("""
            SELECT
            	`user`.`nick_name`,
            	`user`.`account`,
            	`user`.`phone`,
            	`user`.`mail`,
            	`user`.`last_login_time`,
            	`user`.`disabled`,
            	GROUP_CONCAT( `role`.`name` ) roles\s
            FROM
            	admin_user `user`
            	LEFT JOIN admin_user_role `u_role` ON `u_role`.`user_id` = `user`.`id`\s
            	LEFT JOIN admin_role `role` ON `role`.`id` = `u_role`.`role_id`\s
             	LEFT JOIN admin_user_extra `extra` ON `extra`.`user_id` = `user`.`id`\s
            ${ew.customSqlSegment}\s
            GROUP BY
            	`user`.`id`\s""")
    IPage<AdminAccountRes> selectUserList(Page<AdminUser> adminUserPage, @Param("ew") QueryWrapper<AdminUser> wrapper);

    @Select("""
            SELECT
            \t`user`.`id`,\s
            \t`user`.`account`,\s
            \t`user`.`nick_name`,\s
            \t`user`.`avatar`,\s
            \t`user`.`phone`,\s
            \t`user`.`mail`,\s
            \t`user`.`last_login`,\s
            \t`user`.`last_login_time`,\s
            \t`extra`.`sex`,\s
            \tROUND(DATEDIFF(CURDATE(), `extra`.`birth`)/365.2422) `age`,\s
            \t`extra`.`birth`,\s
            \t`extra`.`region`,\s
            \t`extra`.`id_card_no`,\s
            \t`extra`.`id_card_name`,\s
            \t`extra`.`id_card_address`
            FROM
            \t`admin_user` `user`
            \tLEFT JOIN `admin_user_extra` `extra` ON `extra`.`user_id` = `user`.`id`
            WHERE `user`.`account` = #{account} \t""")
    AdminAccountDetailRes selectAccountDetail(@Param("account") String account);
}
