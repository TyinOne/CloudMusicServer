package com.tyin.cloud.service.admin;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.model.res.AdminVersionRes;
import com.tyin.cloud.model.valid.InsertVersionValid;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/6/2 13:45
 * @description ...
 */
public interface IAdminVersionService {
    /**
     * 版本控制列表
     * @param startTime 发布时间
     * @param stopTime  发布时间
     * @param current 当前页
     * @param size 长度
     * @return pageResult
     */
    PageResult<AdminVersionRes, ?> getVersionList(Date startTime, Date stopTime, Long current, Long size);

    /**
     * 新建发行版/补丁
     * @param valid 版本信息
     * @return 受影响行数
     */
    Integer addVersion(InsertVersionValid valid);
}
