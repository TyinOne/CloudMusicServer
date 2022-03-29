package com.tyin.cloud.core.interceptor;

import com.tyin.cloud.core.annotations.Open;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.StringUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/3/26 3:08
 * @description ...
 */
@Configuration
public class AuthAdminUserInterceptor implements HandlerInterceptor {
    @Resource
    private RedisComponents redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        Asserts.isTrue(response.getStatus() != 404, ResultCode.NOT_FOUND);
        Open open = handlerMethod.getMethodAnnotation(Open.class);
        if (Objects.nonNull(open)) {
            return true;
        }
        String token = request.getHeader("token");
        boolean hasCache = redisService.existsKey(token);
        Asserts.isTrue(StringUtils.isEmpty(token) || hasCache, ResultCode.SIGNATURE_NOT_MATCH);
        return true;
    }
}
