package com.tyin.server.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

/**
 * @author Tyin
 * @date 2022/3/26 3:03
 * @description ...
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 允许访问的客户端域名
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setMaxAge(3600L);
        // 允许服务端访问的客户端请求头
        config.addAllowedHeader("*");
        // 允许访问的方法名,GET POST等
        config.addAllowedMethod("*");
        // 对接口配置跨域设置
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
