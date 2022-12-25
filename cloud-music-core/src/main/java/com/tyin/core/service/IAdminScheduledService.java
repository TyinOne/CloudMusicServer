package com.tyin.core.service;

import com.tyin.core.api.PageResult;
import com.tyin.core.module.bean.ScheduledGroupLabel;
import com.tyin.core.module.entity.AdminScheduledLog;
import com.tyin.core.module.res.admin.AdminScheduleRes;
import com.tyin.core.module.res.admin.AdminScheduledDetail;
import com.tyin.core.module.valid.SaveScheduledValid;
import org.quartz.SchedulerException;

import java.util.List;

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
    Integer saveScheduled(SaveScheduledValid valid) throws SchedulerException;

    List<ScheduledGroupLabel> getGroupLabel();

    /**
     * 定时任务搜索列表
     *
     * @param keywords 关键词
     * @param group    分组
     * @param disabled 是否禁用
     * @param size     页长度
     * @param current  当前页
     * @return list
     */
    PageResult<AdminScheduleRes, ?> getSchedulePageResult(String keywords, String group, Boolean disabled, Long size, Long current);

    /**
     * @param id 任务id;
     * @return detail;
     */
    AdminScheduledDetail getScheduled(Long id);
}
