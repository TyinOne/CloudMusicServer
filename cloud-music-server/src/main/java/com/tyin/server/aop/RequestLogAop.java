package com.tyin.server.aop;


import com.google.common.collect.Maps;
import com.tyin.core.annotations.NoLog;
import com.tyin.core.enums.ResultCode;
import com.tyin.core.exception.ApiException;
import com.tyin.core.module.bean.AuthAdminUser;
import com.tyin.core.module.entity.RequestLog;
import com.tyin.core.module.res.admin.AdminUserLoginRes;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.JsonUtils;
import com.tyin.server.api.Result;
import com.tyin.server.auth.security.utils.SecurityUtils;
import com.tyin.server.service.IRequestLogService;
import com.tyin.server.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static com.tyin.server.utils.IpUtils.getIpAddress;


/**
 * @author Tyin
 * @date 2022/3/30 22:22
 * @description ...
 */

@Component
@Slf4j
@RequiredArgsConstructor
@Aspect
public class RequestLogAop implements Ordered {
    private final IRequestLogService requestLogService;

    @Pointcut("execution(public * com.tyin.server.controller..*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object requestLog(ProceedingJoinPoint joinPoint) throws ApiException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method classMethod = signature.getMethod();
        NoLog classNoLog = classMethod.getDeclaringClass().getAnnotation(NoLog.class);
        NoLog methodNoLog = classMethod.getAnnotation(NoLog.class);
        if (Objects.nonNull(classNoLog) || Objects.nonNull(methodNoLog)) {
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                Asserts.fail(e.getMessage());
            }
        }
        log.info("=====================================Method  start====================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Asserts.isTrue(Objects.nonNull(attributes), "无效请求");
        HttpServletRequest request = attributes.getRequest();
        RequestLog.RequestLogBuilder builder = RequestLog.builder();
        Map<String, Object> paramsMap = Maps.newHashMap();
        Enumeration<String> headerNames = request.getHeaderNames();
        String headers = "";
        Map<String, String> headersMap = Maps.newHashMap();
        String params = "";
        try {
            boolean isJson = Boolean.FALSE;
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            Object[] args = joinPoint.getArgs();
            while (headerNames.hasMoreElements()) {
                String next = headerNames.nextElement();
                isJson = "application/json".equals(request.getHeader(next).toLowerCase(Locale.ROOT));
                headersMap.put(next, request.getHeader(next));
            }
            headers = JsonUtils.toJSONString(headersMap);
            if (isJson) {
                params = JsonUtils.toJSONString(Objects.isNull(args[0]) ? new Object() : args[0]);
            } else {
                for (int i = 0; i < argNames.length; i++) {
                    String item = argNames[i];
                    if (!(args[i] instanceof HttpServletRequest) && !(args[i] instanceof AuthAdminUser)) {
                        paramsMap.put(item, args[i]);
                    }
                }
                params = JsonUtils.toJSONString(paramsMap);
            }
        } catch (Exception e) {
            log.warn("Json序列化失败：" + e.getMessage());
        }
        String uri = request.getRequestURI();
        String ip = getIpAddress(request);
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String requestMethod = request.getMethod();
        Long aLong = IpUtils.ipToLong(ip);
        builder.params(params).uri(uri).ip(aLong).method(method);
        builder.requestMethod(requestMethod);
        builder.headers(headers);
        builder.isLogin(Boolean.FALSE);
        AdminUserLoginRes loginUser;
        try {
            loginUser = SecurityUtils.getLoginUser();
            builder.account(loginUser.getAccount());
            builder.isLogin(Boolean.TRUE);
        } catch (ApiException ignored) {
        }
        long start = System.currentTimeMillis();
        log.info("URI          :" + uri);
        log.info("IP           :" + ip);
        log.info("METHOD       :" + method);
        log.info("PARAMS       :" + params);
        String resultStr;
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            resultStr = JsonUtils.toJSONString(result);
            builder.status(Boolean.TRUE);
            builder(builder, start, resultStr, end);
            return result;
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            Integer code = ResultCode.FAIL.getCode();
            if (e instanceof ApiException) {
                code = ((ApiException) e).getErrorCode();
            }
            Result<?> result = new Result<>(code, e.getMessage());
            resultStr = JsonUtils.toJSONString(result);
            builder.status(Boolean.FALSE);
            builder(builder, start, resultStr, end);
            throw new ApiException(code, e.getMessage(), e);
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
        RequestLog requestLog = builder.build();
        requestLogService.save(requestLog);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
