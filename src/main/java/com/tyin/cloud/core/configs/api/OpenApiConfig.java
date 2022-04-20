package com.tyin.cloud.core.configs.api;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyin
 * @date 2022/3/26 3:02
 * @description ...
 */
@Configuration
public class OpenApiConfig {
    private static final String HEADER_NAME = "Authentication";
    private static final String ENV = "env";

    @Bean
    public OpenAPI initOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("Cloud-Music API")
                        .description("OpenAPI")
                        .version("v0.0.1")
        ).components(getComponents()).addSecurityItem(new SecurityRequirement().addList(HEADER_NAME).addList(ENV));
    }

    private Components getComponents() {
        Components components = new Components();
        components.addSecuritySchemes(HEADER_NAME, new SecurityScheme()
                        .name(HEADER_NAME)
                        .scheme("basic")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER))
                .addSecuritySchemes(ENV, new SecurityScheme()
                        .name(ENV)
                        .scheme("basicEnv")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER));
        return components;
    }
}