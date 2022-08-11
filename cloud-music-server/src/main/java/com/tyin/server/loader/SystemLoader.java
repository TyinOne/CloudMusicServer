package com.tyin.server.loader;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.tyin.core.components.CloudTimerTaskComponents;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.components.ScheduledComponents;
import com.tyin.core.components.properties.models.AdminConfig;
import com.tyin.core.components.properties.models.OssConfig;
import com.tyin.core.components.properties.models.TencentMapConfig;
import com.tyin.core.module.base.TimerTaskState;
import com.tyin.core.module.entity.AdminDict;
import com.tyin.core.module.entity.AdminInviteCode;
import com.tyin.core.module.entity.AdminScheduled;
import com.tyin.core.utils.DateUtils;
import com.tyin.core.utils.JsonUtils;
import com.tyin.server.components.properties.PropertiesComponents;
import com.tyin.server.repository.AdminDictRepository;
import com.tyin.server.repository.AdminInviteCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tyin.core.constants.RedisKeyConstants.*;

/**
 * @author Tyin
 * @date 2022/5/22 5:36
 * @description ...
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SystemLoader {
    private final PropertiesComponents propertiesComponents;
    private final RedisComponents redisComponents;
    private final ScheduledComponents scheduledComponents;
    private final CloudTimerTaskComponents timerTaskComponents;
    private final AdminDictRepository adminDictRepository;
    private final AdminInviteCodeRepository adminInviteCodeRepository;

    @Async
    public void initOss() {
        log.info("oss is loading!");
        OssConfig ossConfig = new OssConfig();
        List<AdminDict> oss = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "oss"));
        ossConfig.setOssFileHost(oss.stream().filter(i -> StringUtils.equals("oss_file_host", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssServerUri(oss.stream().filter(i -> StringUtils.equals("oss_server_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileUriTmp(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_tmp", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileUriImages(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_images", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileHotDownloads(oss.stream().filter(i -> StringUtils.equals("oss_update_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFilePackageDownloads(oss.stream().filter(i -> StringUtils.equals("oss_package_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        redisComponents.saveAsync(OSS_PROPERTIES, JsonUtils.toJSONString(ossConfig));
        propertiesComponents.setOss(ossConfig);
    }

    @Async
    public void initMap() {
        log.info("tencentMap is loading!");
        TencentMapConfig tencentMapConfig = new TencentMapConfig();
        List<AdminDict> oss = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "map"));
        tencentMapConfig.setKey(oss.stream().filter(i -> StringUtils.equals("key", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setSecretKey(oss.stream().filter(i -> StringUtils.equals("secret_key", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapApiHost(oss.stream().filter(i -> StringUtils.equals("map_api_host", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapApiUri(oss.stream().filter(i -> StringUtils.equals("map_api_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapDistrictDataVersion(oss.stream().filter(i -> StringUtils.equals("map_district_data_version", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        redisComponents.saveAsync(TENCENT_MAP_PROPERTIES, JsonUtils.toJSONString(tencentMapConfig));
        propertiesComponents.setTencentMap(tencentMapConfig);
    }

    @Async
    public void initAdminConfig() {
        List<AdminDict> adminConfigDict = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "admin_config"));
        AdminConfig adminConfig = AdminConfig.builder()
                .defaultAvatar(adminConfigDict.stream().filter(i -> StringUtils.equals("default_avatar", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""))
                .inviteCodeExpiration(Integer.valueOf(adminConfigDict.stream().filter(i -> StringUtils.equals("invite_code_expiration", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse("")))
                .build();
        propertiesComponents.setAdminConfig(adminConfig);
        redisComponents.saveAsync(ADMIN_CONFIG_PROPERTIES, JsonUtils.toJSONString(adminConfig));
    }

    @Async
    public void initScheduled() throws SchedulerException {
        List<AdminScheduled> scheduledList = Lists.newArrayList();
        scheduledComponents.init(scheduledList);
    }

    @Async
    public void startTimerTask() {
        List<TimerTaskState> list = Lists.newArrayList();
        //把数据库已使用或者已过期的状态改为失效
        //AdminInviteCode任务
        adminInviteCodeRepository.update(AdminInviteCode.builder().invalid(Boolean.TRUE).build(), Wrappers.<AdminInviteCode>lambdaUpdate().le(AdminInviteCode::getExpirationTime, DateUtils.getNowDate()));
        List<AdminInviteCode> adminInviteCode = adminInviteCodeRepository.selectList(Wrappers.<AdminInviteCode>lambdaQuery().eq(AdminInviteCode::getInvalid, Boolean.FALSE));
        list.addAll(adminInviteCode);
        timerTaskComponents.init(list);
    }

}