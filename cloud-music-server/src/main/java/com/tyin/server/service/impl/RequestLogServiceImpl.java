package com.tyin.server.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.module.entity.RequestLog;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.server.utils.IpUtils;
import com.tyin.server.api.PageResult;
import com.tyin.server.repository.RequestLogRepository;
import com.tyin.server.service.IRequestLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageResult<AdminLogRes, ?> queryLog(Long size, Long current) {
        IPage<AdminLogRes> resPage = requestLogRepository.selectLogPage(new Page<>(current, size),
                Wrappers.lambdaQuery()
        );
        List<AdminLogRes> records = resPage.getRecords();
        for (AdminLogRes item : records) {
            item.setIp(IpUtils.longToIp(Long.parseLong(item.getIp())));
            item.setMethod(item.getMethod().substring("com.tyin.cloud.controller".length() + 1));
            item.setElapsed(item.getElapsed() + "ms");
        }
        resPage.setRecords(records);
        return PageResult.buildResult(resPage);
    }
}
