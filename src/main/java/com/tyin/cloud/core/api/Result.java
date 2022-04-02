package com.tyin.cloud.core.api;

import com.tyin.cloud.core.enums.ResultCode;
import com.tyin.cloud.core.exception.handle.BaseErrorInfoInterface;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/3/26 2:49
 * @description ...
 */
@Data
public class Result<T> {

    private Integer code;

    private String message;

    private T result;

    private Long timestamp = System.currentTimeMillis();

    public Result(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 成功返回结果
     */
    public static <T> Result<T> success(T result) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), result);
    }

    /**
     * 成功返回空对象
     */

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    /**
     * 成功返回结果
     *
     * @param result  获取的数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(T result, String message) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, result);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static <T> Result<T> failed(String message, T result) {
        return new Result<>(ResultCode.FAIL.getCode(), message, result);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed(String message) {
        return new Result<>(ResultCode.FAIL.getCode(), message);
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed(BaseErrorInfoInterface errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 失败返回结果
     */
    public static <T> Result<T> failed(Integer code, String message) {
        return new Result<>(code, message);
    }
}