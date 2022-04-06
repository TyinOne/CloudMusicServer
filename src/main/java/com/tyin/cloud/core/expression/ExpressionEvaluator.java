package com.tyin.cloud.core.expression;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tyin
 * @date 2022/4/6 14:34
 * @description ...
 */
public class ExpressionEvaluator<T> extends CachedExpressionEvaluator {

    private final ParameterNameDiscoverer paramNameDiscoverer = new DefaultParameterNameDiscoverer();

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);

    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>(64);

    /**
     * Create the suitable {@link EvaluationContext} for the specified event handling
     * on the specified method.
     */
    public MethodBasedEvaluationContext createEvaluationContext(Object object, Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        ExpressionRootObject root = new ExpressionRootObject(object, args);
        return new MethodBasedEvaluationContext(root, targetMethod, args, this.paramNameDiscoverer);
    }

    /**
     * Specify if the condition defined by the specified expression matches.
     */
    public T condition(String conditionExpression, AnnotatedElementKey elementKey, EvaluationContext evalContext, Class<T> clazz) {
        Expression expression = getExpression(this.conditionCache, elementKey, conditionExpression);
        return expression.getValue(evalContext, clazz);
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }
}
