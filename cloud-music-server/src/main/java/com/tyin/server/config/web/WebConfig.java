package com.tyin.server.config.web;


import com.google.common.collect.Lists;
import com.tyin.core.components.PropertiesComponents;
import com.tyin.server.auth.resolver.AuthUserMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/3/26 3:17
 * @description ...
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthUserMethodArgumentResolver authUserMethodArgumentResolver;
    private final PropertiesComponents properties;

    @Override
    public void addInterceptors(@NotNull InterceptorRegistry registry) {
        List<String> excludeList = Lists.newArrayList();
        excludeList.add("/doc.html");
        excludeList.add("/swagger-ui.html");
        excludeList.add("/swagger-ui/index.html");
        excludeList.add("/swagger-resources");
        excludeList.add("/api-docs");
        excludeList.add("/v2/**");
        excludeList.add("/v3/**");
//        excludeList.add(properties.getAdminPrefix() + "/user/login");
//        excludeList.add(properties.getAdminPrefix() + "/user/register");
//        registry.addInterceptor(authUserInterceptor).addPathPatterns("/**").excludePathPatterns(excludeList);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserMethodArgumentResolver);
    }
}
