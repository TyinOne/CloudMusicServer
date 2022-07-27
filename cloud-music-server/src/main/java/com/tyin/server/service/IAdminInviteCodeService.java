package com.tyin.server.service;

import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.bean.InviteCodeBean;
import com.tyin.core.module.entity.AdminInviteCode;

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
}
