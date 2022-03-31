package com.tyin.cloud.core.aop;

import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.IpUtils;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.entity.RequestLog;
import com.tyin.cloud.service.common.IRequestLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/3/30 22:22
 * @description ...
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestLogAop {
    private final IRequestLogService requestLogService;

    @Pointcut("execution(public * com.tyin.cloud.controller..*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object requestLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("=====================================Method  start====================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Asserts.isTrue(Objects.nonNull(attributes), "无效请求");
        HttpServletRequest request = attributes.getRequest();

        RequestLog.RequestLogBuilder builder = RequestLog.builder();
        String params = JsonUtils.toJSONString(joinPoint.getArgs());
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        builder.params(params).uri(uri).ip(IpUtils.ipToInt(ip)).method(method);
        long start = System.currentTimeMillis();
        long elapsed;
        log.info("URI          :" + uri);
        log.info("IP           :" + ip);
        log.info("METHOD       :" + method);
        log.info("PARAMS       :" + params);
        String resultStr;
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            resultStr = JsonUtils.toJSONString(result);
            builder(builder, start, resultStr, end);
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            resultStr = JsonUtils.toJSONString(Result.failed(e.getMessage()));
            builder(builder, start, resultStr, end);
            throw e;
        }
    }

    private void builder(RequestLog.RequestLogBuilder builder, long start, String resultStr, long end) {
        long elapsed;
        elapsed = end - start;
        builder.result(resultStr);
        builder.elapsed(elapsed);
        log.info("RESULT       :" + resultStr);
        log.info("ELAPSED      :" + elapsed + " ms");
        log.info("=====================================Method  End====================================");
        requestLogService.save(builder.build());
    }
}
