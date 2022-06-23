package com.tyin.core.components.properties.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/3/29 17:38
 * @description ...
 */
@Configuration
@ConfigurationProperties(prefix = "cloud.api.prefix")
public class ApiPrefixConfig {
    private String admin;
    private String client;

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
