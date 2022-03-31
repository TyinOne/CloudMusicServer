package com.tyin.cloud.service.common.impl;

import com.tyin.cloud.model.entity.RequestLog;
import com.tyin.cloud.repository.common.RequestLogRepository;
import com.tyin.cloud.service.common.IRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class RequestLogServiceImpl implements IRequestLogService {

    private final RequestLogRepository requestLogRepository;

    @Override
    @Async
    public void save(RequestLog log) {
        requestLogRepository.insert(log);
    }
}
