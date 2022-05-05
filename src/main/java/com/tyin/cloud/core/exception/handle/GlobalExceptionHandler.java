package com.tyin.cloud.core.exception.handle;

import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.exception.ApiException;
import com.tyin.cloud.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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
        e.printStackTrace();
        log.error(e.getMessage(), e.fillInStackTrace());
        return Result.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error(e.getMessage(), e.fillInStackTrace());
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        String defaultMessage = fieldErrors.get(0).getDefaultMessage();
        return Result.failed(defaultMessage);
    }

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public Result<?> handle(ApiException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode(), e.getMessage());
        }
        if (StringUtils.isNotBlank(e.getMessage())) {
            return Result.failed(e.getMessage());
        }
        return Result.failed(ResultCode.FAIL);
    }
}
