package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyin.cloud.core.api.PageResult;
import com.tyin.cloud.core.utils.DateUtils;
import com.tyin.cloud.core.utils.VersionUtils;
import com.tyin.cloud.model.entity.AdminVersion;
import com.tyin.cloud.model.res.AdminVersionRes;
import com.tyin.cloud.model.valid.InsertVersionValid;
import com.tyin.cloud.repository.admin.AdminVersionMapper;
import com.tyin.cloud.service.admin.IAdminVersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/6/2 13:45
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminVersionServiceImpl implements IAdminVersionService {
    private final AdminVersionMapper adminVersionMapper;

    @Override
    public PageResult<AdminVersionRes, ?> getVersionList(Date startTime, Date stopTime, Long current, Long size) {
        Page<AdminVersionRes> page = new Page<>(current, size);
        if (Objects.nonNull(stopTime)) stopTime = DateUtils.addDays(stopTime, 1);
        IPage<AdminVersionRes> iPage = adminVersionMapper.selectPageRes(page, Wrappers.<AdminVersion>lambdaQuery()
                .between(Objects.nonNull(startTime) && Objects.nonNull(stopTime), AdminVersion::getReleaseTime, startTime, stopTime)
                .orderByDesc(AdminVersion::getReleaseTime)
        );
        return PageResult.buildResult(iPage);
    }

    @Override
    public Integer addVersion(InsertVersionValid valid) {
        AdminVersion version = AdminVersion.builder()
                .version(VersionUtils.stringToInt(valid.getVersion()))
                .hash(valid.getHash())
                .md5(valid.getMd5())
                .releaseTime(null)
                .latest(Boolean.FALSE)
                .forced(valid.getForced())
                .releaseTime(new Date())
                .updateLog(valid.getUpdateLog())
                .src(valid.getSrc())
                .build();
        return adminVersionMapper.insert(version);
    }
}
