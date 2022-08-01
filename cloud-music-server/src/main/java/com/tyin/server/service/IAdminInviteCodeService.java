package com.tyin.server.service;

import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.server.api.PageResult;
import com.tyin.core.module.res.admin.AdminInviteCodeRes;

/**
 * @author Tyin
 * @date 2022/7/22 18:02
 * @description ...
 */
public interface IAdminInviteCodeService {
    /**
     * 生成邀请码
     * @param id 角色ID
     * @param user 操作人
     * @return 邀请码
     */
    InviteCodeBean generateInviteCode(Long id, AuthAdminUser user);

    /**
     * 使邀请码过期
     * @param code 邀请码
     */
    void handleInviteCodeExpire(String code);

    /**
     * 使邀请码被使用
     * @param code 邀请码
     * @param account 使用人
     */
    void handleInviteCodeUse(String code, String account);

    /**
     * 查询邀请码数据
     * @param code 邀请码
     * @return entity
     */
    AdminInviteCode getInviteCode(String code);

    /**
     * 查询邀请码列表
     * @param useBy 使用者
     * @param createBy 创建者
     * @param invalid 是否无效
     * @param isUsed 是否被使用
     * @param current 当前页
     * @param size 页长度
     * @return PageResult<AdminInviteCodeRes,?>
     */
    PageResult<AdminInviteCodeRes,?> getList(String useBy, String createBy, Boolean invalid, Boolean isUsed, Long current, Long size);

    /**
     * 清理邀请码
     * @param id 邀请码ID
     * @return 受影响行数
     */
    Integer remove(Long id);
}
