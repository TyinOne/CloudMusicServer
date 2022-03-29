package com.tyin.cloud.core.exception.handle;

import com.tyin.cloud.core.api.Result;
import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.exception.ApiException;
import com.tyin.cloud.core.utils.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * @author Tyin
 * @date 2022/3/26 2:48
 * @description ...
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<Map<String, String>> handle(Exception e) {
        e.printStackTrace();
        return Result.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public Result<Map<String, String>> handle(ApiException e) {
        e.printStackTrace();
        if (e.getErrorCode() != null) {
            return Result.failed(e.getErrorCode(), e.getMessage());
        }
        if (StringUtils.isNotBlank(e.getMessage())) {
            return Result.failed(e.getMessage());
        }
        return Result.failed(ResultCode.FAIL);
    }
}
