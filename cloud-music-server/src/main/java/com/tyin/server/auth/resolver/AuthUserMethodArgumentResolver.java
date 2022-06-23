package com.tyin.server.auth.resolver;

import com.tyin.core.annotations.Auth;
import com.tyin.core.components.RedisComponents;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.server.service.IUserCacheService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.tyin.core.constants.CommonConstants.TOKEN;
import static com.tyin.core.constants.RedisKeyConstants.ADMIN_USER_TOKEN_PREFIX;

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
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(TOKEN);
        String userCacheStr = redisComponents.get(ADMIN_USER_TOKEN_PREFIX + token);
        return userCacheService.getUserCache(userCacheStr, AuthAdminUser.class);
    }
}
