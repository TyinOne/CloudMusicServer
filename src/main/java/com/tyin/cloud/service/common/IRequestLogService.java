package com.tyin.cloud.service.common;

import com.tyin.cloud.model.entity.RequestLog;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
public interface IRequestLogService {
    void save(RequestLog log);
}
