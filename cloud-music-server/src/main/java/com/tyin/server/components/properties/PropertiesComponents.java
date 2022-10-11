package com.tyin.server.components.properties;

import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.components.properties.models.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    private final OkHttpConfig okHttpConfig;
    private final ApiPrefixConfig apiPrefixConfig;


    public void setModules(PropertiesEnum propertiesEnum, Object obj) {
        switch (propertiesEnum) {
            case OSS -> setOss((OssConfig) obj);
            case MAP -> setTencentMap((TencentMapConfig) obj);
            case ADMIN -> setAdminConfig((AdminConfig) obj);
            default -> log.warn("UnKnown propertiesEnumType : {}", propertiesEnum.getType());
        }
    }

    private void setTencentMap(TencentMapConfig config) {
        tencentMapConfig = config;
    }

    private void setOss(OssConfig config) {
        oss = config;
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

    private void setAdminConfig(AdminConfig config) {
        adminConfig = config;
    }
}
