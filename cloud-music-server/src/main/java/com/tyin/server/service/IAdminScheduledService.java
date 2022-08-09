package com.tyin.server.service;

import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.server.params.valid.InsertScheduledValid;

/**
 * @author Tyin
 * @date 2022/7/14 9:42
 * @description ...
 */
public interface IAdminScheduledService {
    /**
     * 添加任务日志
     *
     * @param adminScheduledLog 任务日志
     */
    void addLog(AdminScheduledLog adminScheduledLog);

    /**
     * 新增任务
     *
     * @param valid 任务实体
     * @return 受影响行数
     */
    Integer addScheduled(InsertScheduledValid valid);
}
