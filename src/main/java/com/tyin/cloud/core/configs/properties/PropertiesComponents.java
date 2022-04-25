package com.tyin.cloud.core.configs.properties;

import com.tyin.cloud.core.configs.properties.models.ApiPrefixConfig;
import com.tyin.cloud.core.configs.properties.models.OkHttpConfig;
import com.tyin.cloud.core.configs.properties.models.OssConfig;
import com.tyin.cloud.core.configs.properties.models.TencentMapConfig;
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
    private final OkHttpConfig okHttpConfig;
    private final ApiPrefixConfig apiPrefixConfig;
    private final OssConfig ossConfig;
    private final TencentMapConfig tencentMapConfig;

    public String getOssUrl() {
        return ossConfig.getUrl();
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
}
