package com.tyin.cloud.core.configs.properties.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/3/29 17:38
 * @description ...
 */
@Configuration
@ConfigurationProperties(prefix = "cloud.api.prefix")
@Data
public class ApiPrefixConfig {
    private String admin;
    private String client;
}
