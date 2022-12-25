package com.tyin.server.auth.security.config;

import com.tyin.core.auth.admin.TokenService;
import com.tyin.core.components.RedisComponents;
import com.tyin.server.auth.AdminPermissionService;
import com.tyin.server.auth.security.filter.AuthenticationFilter;
import com.tyin.server.auth.security.filter.JWTLoginFilter;
import com.tyin.server.auth.security.point.Http401AuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Tyin
 * @date 2022/9/28 18:00
 * @description ...
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final static String[] AUTH_WHITELIST = {
            "/admin/user/login",
            "/admin/user/login/t",
            // -- swagger ui
            "/v2/api-docs/*",
            "/v3/api-docs/*",
            "/v3/api-docs",
            "/doc.html",
            "/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/druid/**"
    };
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisComponents redisComponents;
    private final TokenService tokenService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AdminPermissionService adminPermissionService;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint())
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new AuthenticationFilter(tokenService, adminPermissionService, authenticationManager()))
                .logout()
                .and()
//                .authenticationProvider(new CloudAuthenticationProvider(tokenService, userDetailsService, bCryptPasswordEncoder, redisComponents))
                .build();
    }
}
