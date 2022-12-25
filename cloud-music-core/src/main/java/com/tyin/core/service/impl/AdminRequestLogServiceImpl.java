package com.tyin.core.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.core.api.PageResult;
import com.tyin.core.module.entity.AdminRequestLog;
import com.tyin.core.module.res.admin.AdminLogDetailRes;
import com.tyin.core.module.res.admin.AdminLogRes;
import com.tyin.core.repository.admin.AdminRequestLogRepository;
import com.tyin.core.service.IAdminRequestLogService;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.DateUtils;
import com.tyin.core.utils.IpUtils;
import com.tyin.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.tyin.core.constants.ResMessageConstants.DATA_ERROR;

/**
 * @author Tyin
 * @date 2022/3/30 23:35
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AdminRequestLogServiceImpl implements IAdminRequestLogService {

    private final AdminRequestLogRepository adminRequestLogRepository;

    @Override
    @Async
    public void save(AdminRequestLog log) {
        adminRequestLogRepository.insert(log);
    }

    @Override
    public PageResult<AdminLogRes, ?> queryLog(Date startDate, Date endDate, String method, Boolean status, String keywords, Long size, Long current) {
        LambdaQueryWrapper<AdminRequestLog> wrapper = Wrappers.lambdaQuery();
        wrapper
                .eq(StringUtils.isNotEmpty(method), AdminRequestLog::getRequestMethod, method)
                .eq(Objects.nonNull(status), AdminRequestLog::getStatus, status);
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            endDate = DateUtils.getEndForDay(endDate);
            wrapper.between(AdminRequestLog::getCreated, startDate, endDate);
        }
        wrapper.apply(StringUtils.isNotEmpty(keywords), "INSTR(`uri`, {0}) > 0", keywords);
        IPage<AdminLogRes> resPage = adminRequestLogRepository.selectLogPage(new Page<>(current, size), wrapper);
        List<AdminLogRes> records = resPage.getRecords();
        for (AdminLogRes item : records) {
            item.setIp(IpUtils.longToIp(Long.parseLong(item.getIp())));
            item.setMethod(item.getMethod().substring("com.tyin.cloud.controller".length() + 2));
            item.setElapsed(item.getElapsed() + "ms");
        }
        resPage.setRecords(records);
        return PageResult.buildResult(resPage);
    }

    @Override
    public AdminLogDetailRes queryLogDetail(Long logId) {
        AdminRequestLog requestLog = adminRequestLogRepository.selectOne(Wrappers.<AdminRequestLog>lambdaQuery().eq(AdminRequestLog::getId, logId));
        Asserts.isTrue(Objects.nonNull(requestLog), DATA_ERROR);
        String address = "";
        try {
            address = IpUtils.getIpCity(requestLog.getIp());
            address = address.replace("0|", "");
            address = address.replace("|", "-");
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("ip转换失败: " + IpUtils.longToIp(requestLog.getIp()));
            log.warn(e.getMessage());
        }
        return AdminLogDetailRes.builder()
                .id(requestLog.getId())
                .time(requestLog.getCreated())
                .params(requestLog.getParams())
                .result(requestLog.getResult())
                .uri(requestLog.getUri())
                .account(requestLog.getAccount())
                .address(address)
                .headers(requestLog.getHeaders())
                .build();
    }
}
