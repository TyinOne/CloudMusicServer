package com.tyin.server.config.web;


import com.google.common.collect.Lists;
import com.tyin.server.auth.resolver.AuthUserMethodArgumentResolver;
import com.tyin.server.components.properties.PropertiesComponents;
import lombok.RequiredArgsConstructor;
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
    public void addInterceptors(InterceptorRegistry registry) {
        /*
         * List<String> list = Lists.newArrayList();
         *         list.add("/doc.html");
         *         list.add("/swagger-ui.html");
         *         list.add("/swagger-resources");
         *         list.add("/api-docs");
         *         list.add("/v2/api-docs");
         *         list.add("/v3/api-docs");
         *         list.add("/error");
         */
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
