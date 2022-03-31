package com.tyin.cloud.core.configs.properties;

import com.tyin.cloud.core.configs.properties.models.ApiPrefixConfig;
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
    private final ApiPrefixConfig apiPrefixConfig;


    public String getAdminPrefix() {
        return this.apiPrefixConfig.getAdmin();
    }

    public String getClientPrefix() {
        return this.apiPrefixConfig.getClient();
    }
}
