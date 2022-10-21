package com.tyin.server.service;


import com.tyin.core.module.entity.RequestLog;
import com.tyin.core.module.res.admin.AdminLogDetailRes;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.server.api.PageResult;

import java.util.Date;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
public interface IRequestLogService {
    void save(RequestLog log);

    PageResult<AdminLogRes, ?> queryLog(Date startDate, Date endDate, String keywords, Long size, Long current);

    /**
     * 日志详情
     *
     * @param logId 日志id
     * @return 日志详情
     */
    AdminLogDetailRes queryLogDetail(Long logId);
}
