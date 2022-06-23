package com.tyin.server.components.properties;

import com.tyin.core.components.properties.models.ApiPrefixConfig;
import com.tyin.core.components.properties.models.OkHttpConfig;
import com.tyin.core.components.properties.models.OssConfig;
import com.tyin.core.components.properties.models.TencentMapConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/3/29 17:37
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class PropertiesComponents {
    private static OssConfig oss;
    private final OkHttpConfig okHttpConfig;
    private final ApiPrefixConfig apiPrefixConfig;
    private static TencentMapConfig tencentMapConfig;

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
        return oss.getOssFileHotDownloads();
    }
    public String getOssPackageDownloads() {
        return oss.getOssFilePackageDownloads();
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

    public void setTencentMap(TencentMapConfig config) {
        tencentMapConfig = config;
    }

    public void setOss(OssConfig config) {
        oss = config;
    }
}