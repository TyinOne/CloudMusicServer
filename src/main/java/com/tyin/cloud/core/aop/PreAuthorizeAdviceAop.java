package com.tyin.cloud.core.aop;

import com.tyin.cloud.core.annotations.Auth;
import com.tyin.cloud.core.auth.AuthAdminUser;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.utils.Asserts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/4/6 11:50
 * @description ...
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PreAuthorizeAdviceAop {

    @Pointcut("execution(public * com.tyin.cloud.controller..*(..)) && args(..,com.tyin.cloud.core.auth.AuthAdminUser)")
    public void pointcut() {
    }

    @Before("pointcut() && args(.., user)")
    public void authorize(JoinPoint joinPoint, AuthAdminUser user) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter parameter = null;
        Parameter[] parameters = method.getParameters();
        for (Parameter item : parameters) {
            if (item.getType().equals(AuthAdminUser.class)) parameter = item;
        }
        if (Objects.isNull(parameter)) return;

        Auth annotation = parameter.getAnnotation(Auth.class);
        String permission = annotation.value();
        Asserts.isTrue(hasPermission(user, permission), ResultCode.PERMISSION_DENIED);
    }

    private boolean hasPermission(AuthAdminUser user, String permssion) {
        Set<String> permissions = user.getPermissions();
        Asserts.isTrue(Objects.nonNull(permissions) && !permissions.isEmpty(), ResultCode.PERMISSION_DENIED);
        return permissions.contains(permssion);
    }
}
