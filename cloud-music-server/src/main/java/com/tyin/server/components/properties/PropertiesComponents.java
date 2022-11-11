package com.tyin.server.components.properties;

import com.google.common.collect.Lists;
import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.components.properties.models.*;
import com.tyin.core.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/3/29 17:37
 * @description ...
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PropertiesComponents {
    private static OssConfig oss;
    private static TencentMapConfig tencentMapConfig;
    private static AdminConfig adminConfig;
    private static ScheduledGroupConfig scheduledGroupConfig;
    private final OkHttpConfig okHttpConfig;
    private final ApiPrefixConfig apiPrefixConfig;


    public void setModules(PropertiesEnum propertiesEnum, String obj) {
        switch (propertiesEnum) {
            case OSS -> setOss(obj, propertiesEnum.getClazz());
            case MAP -> setTencentMap(obj, propertiesEnum.getClazz());
            case ADMIN -> setAdminConfig(obj, propertiesEnum.getClazz());
            case SCHEDULED_GROUP -> setScheduledGroupConfig(obj, propertiesEnum.getClazz());
            default -> log.warn("UnKnown propertiesEnumType : {}", propertiesEnum.getType());
        }
    }

    private void setScheduledGroupConfig(String objString, Class<?> clazz) {
        ScheduledGroupConfig config = new ScheduledGroupConfig();
        List<ScheduledGroupConfig.ScheduledGroup> list = Lists.newArrayList();
        Map<String, Object> stringObjectMap = JsonUtils.toMap(objString);
        if (Objects.nonNull(stringObjectMap)) {
            stringObjectMap.forEach((k, v) -> {
                list.add(ScheduledGroupConfig.ScheduledGroup.builder().key(k).value(v.toString()).build());
            });
        }
        config.setList(list);
        scheduledGroupConfig = config;
    }

    private void setTencentMap(String objString, Class<?> clazz) {
        tencentMapConfig = (TencentMapConfig) JsonUtils.toJavaObject(objString, clazz);
    }

    private void setOss(String objString, Class<?> clazz) {
        oss = (OssConfig) JsonUtils.toJavaObject(objString, clazz);
    }

    public String getOssUrl() {
        return oss.getOssFileHost();
    }

    public String getOssTmp() {
        return oss.getOssFileUriTmp();
    }

    public String getOssServer() {
        return oss.getOssServerUri();
    }

    public String getOssImages() {
        return oss.getOssFileUriImages();
    }

    public String getOssHotDownloads() {
        return oss.getOssHotDownloads();
    }

    public String getOssPackageUri() {
        return oss.getOssPackageUri();
    }

    public String getAdminPrefix() {
        return this.apiPrefixConfig.getAdmin();
    }

    public String getClientPrefix() {
        return this.apiPrefixConfig.getClient();
    }

    public OkHttpConfig getOkHttpConfig() {
        return okHttpConfig;
    }

    public String getTencentMapKey() {
        return tencentMapConfig.getKey();
    }

    public String getTencentSecretKey() {
        return tencentMapConfig.getSecretKey();
    }

    public String getTencentMapDistrictDataVersion() {
        return tencentMapConfig.getMapDistrictDataVersion();
    }

    public TencentMapConfig getTencentMapConfig() {
        return tencentMapConfig;
    }

    public AdminConfig getAdminConfig() {
        return adminConfig;
    }

    private void setAdminConfig(String objString, Class<?> clazz) {
        adminConfig = (AdminConfig) JsonUtils.toJavaObject(objString, clazz);
    }

    public ScheduledGroupConfig getScheduledGroupConfig() {
        return scheduledGroupConfig;
    }
}
