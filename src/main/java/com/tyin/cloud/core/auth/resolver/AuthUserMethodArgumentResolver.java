package com.tyin.cloud.core.auth.resolver;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.components.RedisComponents;
import com.tyin.cloud.service.common.IUserCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Tyin
 * @date 2022/3/26 3:18
 * @description ...
 */
@Component
@RequiredArgsConstructor
public class AuthUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final RedisComponents redisComponents;
    private final IUserCacheService userCacheService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("token");
        Class<?> parameterType = parameter.getParameterType();
        String userCache = parameterType.equals(AuthAdminUser.class) ? redisComponents.get("user:admin:token:" + token) : redisComponents.get("user:client:token:" + token);
        return userCacheService.getUserCache(userCache, parameterType);
    }
}
