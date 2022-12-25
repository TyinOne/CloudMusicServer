package com.tyin.core.api;

import com.tyin.core.enums.ResultCode;
import com.tyin.core.exception.handle.BaseErrorInfoInterface;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
        return success(ResultCode.SUCCESS.getMessage(), result);
    }

    /**
     * 成功返回空对象
     */

    public static <T> Result<T> success() {
        return success(ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param result  获取的数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(String message, T result) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, result);
    }

    @NotNull
    @Contract(" -> new")
    public static Result<String> failed() {
        return failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static Result<String> failed(String message) {
        return failed(ResultCode.FAIL.getCode(), message);
    }


    /**
     * 失败返回结果
     */
    public static Result<String> failed(BaseErrorInfoInterface errorCode) {
        return failed(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 失败返回结果
     */
    public static Result<String> failed(Integer code, String message) {
        return new Result<>(code, message);
    }
}