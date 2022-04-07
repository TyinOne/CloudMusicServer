package com.tyin.cloud.core.interceptor;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.utils.Asserts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

import static com.tyin.cloud.core.constants.CommonConstants.TOKEN;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;
import static com.tyin.cloud.core.constants.RedisKeyConstants.CLIENT_USER_TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/3/26 3:08
 * @description ...
 */
@Configuration
@RequiredArgsConstructor
public class AuthUserInterceptor implements HandlerInterceptor {

    private final RedisComponents redisComponents;
    private final PropertiesComponents properties;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Asserts.isTrue(response.getStatus() != 404, ResultCode.NOT_FOUND);
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Open open = handlerMethod.getMethodAnnotation(Open.class);
        if (Objects.nonNull(open)) {
            return true;
        }
        String requestURI = request.getRequestURI();
        Asserts.isTrue(requestURI.startsWith(properties.getAdminPrefix()) || requestURI.startsWith(properties.getClientPrefix()), ResultCode.NOT_FOUND);
        String prefix = requestURI.substring(0, requestURI.indexOf("/", 1));
        return authentication(prefix, request);
    }

    private boolean authentication(String prefix, HttpServletRequest request) {
        Asserts.isTrue(redisComponents.existsKey(getTokenPrefix(prefix) + request.getHeader(TOKEN)), ResultCode.SIGNATURE_NOT_MATCH);
        return true;
    }

    private String getTokenPrefix(String value) {
        String adminPrefix = properties.getAdminPrefix();
        String clientPrefix = properties.getClientPrefix();
        if (value.equals(adminPrefix)) {
            return ADMIN_USER_TOKEN_PREFIX;
        } else if (value.equals(clientPrefix)) {
            return CLIENT_USER_TOKEN_PREFIX;
        }
        return "";
    }
}
