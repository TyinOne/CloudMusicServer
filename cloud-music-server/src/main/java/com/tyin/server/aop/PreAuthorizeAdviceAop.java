package com.tyin.server.aop;

import com.tyin.core.annotations.Auth;
import com.tyin.core.enums.ResultCode;
import com.tyin.core.expression.ExpressionEvaluator;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/4/6 11:50
 * @description ...
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PreAuthorizeAdviceAop implements Ordered {

    private final ExpressionEvaluator<Boolean> evaluator = new ExpressionEvaluator<>();
    private final ApplicationContext applicationContext;

    @Pointcut("execution(public * com.tyin.server.controller..*(..)) && args(..,com.tyin.core.module.bean.AuthAdminUser)")
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
        if (StringUtils.isEmpty(annotation.value())) return;
        Asserts.isTrue(getValue(joinPoint, annotation.value()), ResultCode.PERMISSION_DENIED);
    }

    private Boolean getValue(JoinPoint joinPoint, String condition) {
        return getValue(joinPoint.getTarget(), joinPoint.getArgs(),
                joinPoint.getTarget().getClass(),
                ((MethodSignature) joinPoint.getSignature()).getMethod(), condition);
    }

    private Boolean getValue(Object object, Object[] args, Class<?> clazz, Method method, String condition) {
        if (args == null) {
            return null;
        }
        MethodBasedEvaluationContext evaluationContext = evaluator.createEvaluationContext(object, clazz, method, args);
        evaluationContext.setBeanResolver(new BeanFactoryResolver(applicationContext));
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, clazz);
        return evaluator.condition(condition, methodKey, evaluationContext, Boolean.class);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
