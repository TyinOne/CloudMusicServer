package com.tyin.server.service;


import com.tyin.core.module.entity.RequestLog;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.server.api.PageResult;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
public interface IRequestLogService {
    void save(RequestLog log);

    PageResult<AdminLogRes, ?> queryLog(Long size, Long current);
}
