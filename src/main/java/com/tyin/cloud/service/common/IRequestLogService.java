package com.tyin.cloud.service.common;

import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.model.entity.RequestLog;
import com.tyin.cloud.model.res.AdminLogRes;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
public interface IRequestLogService {
    void save(RequestLog log);

    PageResult<AdminLogRes,?> queryLog(Long size, Long current);
}
