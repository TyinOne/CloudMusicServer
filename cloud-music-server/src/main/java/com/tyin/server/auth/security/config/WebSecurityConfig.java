package com.tyin.server.auth.security.config;

import com.tyin.core.components.RedisComponents;
import com.tyin.server.auth.security.CloudAuthenticationProvider;
import com.tyin.server.auth.security.filter.AuthenticationFilter;
import com.tyin.server.auth.security.filter.JWTLoginFilter;
import com.tyin.server.auth.security.point.Http401AuthenticationEntryPoint;
import com.tyin.server.auth.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Tyin
 * @date 2022/9/28 18:00
 * @description ...
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisComponents redisComponents;
    private final TokenService tokenService;

    private final static String[] AUTH_WHITELIST = {"/admin/user/login", "/admin/user/login/t",
            // -- swagger ui
            "/v2/api-docs/*", "/v3/api-docs/*", "/doc.html", "/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                //session禁用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //拦截规则配置
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()  // 所有请求需要身份认证
                //异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                .and()

                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new AuthenticationFilter(tokenService, authenticationManager()))
                .logout()
                .permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        // 使用自定义身份验证组件
        auth.authenticationProvider(new CloudAuthenticationProvider(tokenService, userDetailsService, bCryptPasswordEncoder, redisComponents));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
