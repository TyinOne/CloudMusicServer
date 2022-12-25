package com.tyin.core.repository.admin;


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
             `user`.`account`,
             `user`.`nick_name`,
             CONCAT(#{oss},`user`.`avatar`) AS `avatar`,
             `user`.`phone`,
             `user`.`mail`,
             (SELECT GROUP_CONCAT( `name` ) FROM `admin_role` WHERE `id` IN ( SELECT role_id FROM admin_user_role WHERE user_id = `user`.id )) `role`,
             `extra`.`sex`,
             `extra`.`birth`,
             `extra`.`region`,
             `extra`.`id_card_no`,
             `extra`.`id_card_name`,
             `extra`.`id_card_address`
            FROM
             `admin_user` `user`
            LEFT JOIN `admin_user_extra` `extra` on `extra`.`user_id` = `user`.`id`
            WHERE
             `account` = #{account}
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
            	GROUP_CONCAT( `role`.`name` ) roles
            FROM
            	admin_user `user`
            	LEFT JOIN admin_user_role `u_role` ON `u_role`.`user_id` = `user`.`id`
            	LEFT JOIN admin_role `role` ON `role`.`id` = `u_role`.`role_id`
             	LEFT JOIN admin_user_extra `extra` ON `extra`.`user_id` = `user`.`id`
            WHERE ${ew.sqlSegment}
            GROUP BY
            	`user`.`id`
            """)
    IPage<AdminAccountRes> selectUserList(Page<AdminUser> adminUserPage, @Param("ew") QueryWrapper<AdminUser> wrapper);

    @Select("""
            SELECT
             `user`.`id`,
             `user`.`account`,
             `user`.`nick_name`,
             `user`.`avatar`,
             `user`.`phone`,
             `user`.`mail`,
             `user`.`last_login`,
             `user`.`last_login_time`,
             `extra`.`sex`,
             ROUND(DATEDIFF(CURDATE(), `extra`.`birth`)/365.2422) `age`,
             `extra`.`birth`,
             `extra`.`region`,
             `extra`.`id_card_no`,
             `extra`.`id_card_name`,
             `extra`.`id_card_address`
            FROM
             `admin_user` `user`
             LEFT JOIN `admin_user_extra` `extra` ON `extra`.`user_id` = `user`.`id`
            WHERE `user`.`account` = #{account}
            """)
    AdminAccountDetailRes selectAccountDetail(@Param("account") String account);
}
