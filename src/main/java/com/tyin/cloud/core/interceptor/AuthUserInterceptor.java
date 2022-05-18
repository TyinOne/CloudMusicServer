package com.tyin.cloud.core.interceptor;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.utils.Asserts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

import static com.tyin.cloud.core.constants.CommonConstants.*;
import static com.tyin.cloud.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;

/**
 * @author Tyin
 * @date 2022/3/26 3:08
 * @description ...
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthUserInterceptor implements HandlerInterceptor {

    private final RedisComponents redisComponents;
    private final PropertiesComponents properties;


    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, HttpServletResponse response, @NotNull Object handler) {
        Asserts.isTrue(response.getStatus() != 404, ResultCode.NOT_FOUND);
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Open open = handlerMethod.getMethodAnnotation(Open.class);
        if (Objects.nonNull(open)) {
            return true;
        }
        String requestURI = request.getRequestURI();
        String prefix;
        if (ERROR_URI.equals(requestURI)) return true;
        prefix = requestURI.substring(0, requestURI.indexOf(ROOT_URL, 1));
        return authentication(prefix, request);
    }

    private boolean authentication(String prefix, HttpServletRequest request) {
        Asserts.isTrue(redisComponents.existsKey(ADMIN_USER_TOKEN_PREFIX + request.getHeader(TOKEN)), ResultCode.SIGNATURE_NOT_MATCH);
        return true;
    }
}
