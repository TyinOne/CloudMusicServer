package com.tyin.server.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.tyin.core.module.entity.AdminVersion;
import com.tyin.core.module.res.admin.AdminVersionRes;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.DateUtils;
import com.tyin.core.utils.VersionUtils;
import com.tyin.server.api.PageResult;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.params.valid.InsertVersionValid;
import com.tyin.server.repository.AdminVersionMapper;
import com.tyin.server.service.IAdminVersionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/6/2 13:45
 * @description ...
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminVersionServiceImpl implements IAdminVersionService {
    private final AdminVersionMapper adminVersionMapper;
    private final PropertiesComponents propertiesComponents;

    @Override
    public PageResult<AdminVersionRes, ?> getVersionList(Date startTime, Date stopTime, Long current, Long size) {
        Page<AdminVersionRes> page = new Page<>(current, size);
        if (Objects.nonNull(stopTime)) stopTime = DateUtils.addDays(stopTime, 1);
        IPage<AdminVersionRes> iPage = adminVersionMapper.selectPageRes(page, Wrappers.<AdminVersion>lambdaQuery()
                .between(Objects.nonNull(startTime) && Objects.nonNull(stopTime), AdminVersion::getReleaseTime, startTime, stopTime)
                .orderByDesc(AdminVersion::getReleaseTime)
        );
        iPage.getRecords().forEach(i -> i.setSrc(propertiesComponents.getOssUrl() + i.getSrc()));
        return PageResult.buildResult(iPage);
    }

    @Override
    public Integer addVersion(InsertVersionValid valid) {
        //验证文件
        String fileName = valid.getFileName();
        Asserts.isTrue(fileName.contains("."), "文件类型暂不支持");
        String[] split = fileName.split("\\.");
        String suffix = split[1];
        Asserts.isTrue(Lists.newArrayList("zip", "exe").contains(suffix), "文件类型暂不支持");
        //构建目录
        String tmpPath = propertiesComponents.getOssServer() + propertiesComponents.getOssTmp() + fileName;
        String pathDir = propertiesComponents.getOssServer() + ("zip".equals(suffix) ? propertiesComponents.getOssHotDownloads() : propertiesComponents.getOssPackageUri()) + valid.getName();
        File source = new File(tmpPath);
        File target = new File(pathDir);
        String newSrc = "";
        //转移文件
        try {
            FileUtils.copyFile(source, target);
            newSrc = propertiesComponents.getOssHotDownloads() + valid.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdminVersion version = AdminVersion.builder()
                .version(VersionUtils.stringToInt(valid.getVersion()))
                .hash(valid.getHash())
                .md5(valid.getMd5())
                .releaseTime(null)
                .latest(Boolean.FALSE)
                .forced(valid.getForced())
                .releaseTime(new Date())
                .updateLog(valid.getUpdateLog())
                .src(newSrc)
                .build();
        return adminVersionMapper.insert(version);
    }
}
