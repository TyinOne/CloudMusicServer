package com.tyin.server.interceptor;


import com.tyin.core.annotations.Open;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.enums.ResultCode;
import com.tyin.core.utils.Asserts;
import com.tyin.server.components.properties.PropertiesComponents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.tyin.core.constants.CommonConstants.*;
import static com.tyin.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;

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
        Class<?> declaringClass = handlerMethod.getMethod().getDeclaringClass();
        Open openClass = declaringClass.getAnnotation(Open.class);
        Open openMethod = handlerMethod.getMethodAnnotation(Open.class);
        if (Objects.nonNull(openClass) || Objects.nonNull(openMethod)) {
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
