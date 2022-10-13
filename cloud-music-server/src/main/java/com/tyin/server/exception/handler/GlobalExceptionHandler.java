package com.tyin.server.exception.handler;

import com.tyin.core.enums.ResultCode;
import com.tyin.core.exception.ApiException;
import com.tyin.core.utils.StringUtils;
import com.tyin.server.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Tyin
 * @date 2022/3/26 2:48
 * @description ...
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> handle(Exception e) {
        log.error(e.getMessage(), e.fillInStackTrace());
        return Result.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = AuthenticationException.class)
    public Result<?> handle(AuthenticationException e) {
        log.error(e.getMessage(), e.fillInStackTrace());
        return Result.failed(ResultCode.SIGNATURE_NOT_MATCH);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e.fillInStackTrace());
        String message = "";
        if (e.getBindingResult().getErrorCount() > 0) {
            message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return Result.failed(message);
    }

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public Result<?> handle(ApiException e) {
        log.error(e.getMessage(), e.fillInStackTrace());
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode(), e.getMessage());
        }
        if (StringUtils.isNotBlank(e.getMessage())) {
            return Result.failed(e.getMessage());
        }
        return Result.failed(ResultCode.FAIL);
    }
}
