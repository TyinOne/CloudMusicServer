package com.tyin.cloud.core.configs.properties.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/4/22 1:42
 * @description ...
 */
@Configuration
@ConfigurationProperties(prefix = "cloud.map")
@Data
public class TencentMapConfig {
    private String key;
    private String secretKey;
    private String map_api_host;
    private String map_api_uri;
    private String map_district_data_version;
}
