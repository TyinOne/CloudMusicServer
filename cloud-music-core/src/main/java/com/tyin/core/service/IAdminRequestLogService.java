package com.tyin.core.service;


import com.tyin.core.api.PageResult;
import com.tyin.core.module.entity.AdminRequestLog;
import com.tyin.core.module.res.admin.AdminLogDetailRes;
import com.tyin.core.module.res.admin.AdminLogRes;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
public interface IAdminRequestLogService {
    void save(AdminRequestLog log);

    PageResult<AdminLogRes, ?> queryLog(Date startDate, Date endDate, String method, Boolean status, String keywords, Long size, Long current);

    /**
     * 日志详情
     *
     * @param logId 日志id
     * @return 日志详情
     */
    AdminLogDetailRes queryLogDetail(Long logId);
}
