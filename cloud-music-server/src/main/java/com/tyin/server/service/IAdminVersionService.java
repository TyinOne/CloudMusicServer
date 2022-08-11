package com.tyin.server.service;


import com.tyin.core.module.res.admin.AdminVersionRes;
import com.tyin.server.api.PageResult;
import com.tyin.server.params.valid.InsertVersionValid;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/6/2 13:45
 * @description ...
 */
public interface IAdminVersionService {
    /**
     * 版本控制列表
     *
     * @param startTime 发布时间
     * @param stopTime  发布时间
     * @param current   当前页
     * @param size      长度
     * @return pageResult
     */
    PageResult<AdminVersionRes, ?> getVersionList(Date startTime, Date stopTime, Long current, Long size);

    /**
     * 新建发行版/补丁
     *
     * @param valid 版本信息
     * @return 受影响行数
     */
    Integer addVersion(InsertVersionValid valid);
}
