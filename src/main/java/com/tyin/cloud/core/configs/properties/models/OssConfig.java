package com.tyin.cloud.core.configs.properties.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/4/6 10:03
 * @description ...
 */
@Configuration
@ConfigurationProperties(prefix = "cloud.oss")
@Data
public class OssConfig {
    private String url;
}
