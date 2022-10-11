package com.tyin.server.config.springdoc;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/10/11 9:40
 * @description ...
 */
@Configuration
public class SpringdocConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("CloudMusicServer")
                .packagesToScan("com.tyin.server")
                .build();
    }
}
