package com.tyin.core.exception.handle;

/**
 * @author Tyin
 * @date 2022/3/26 2:47
 * @description ...
 */
public interface BaseErrorInfoInterface {
    /**
     * 错误码
     */
    Integer getCode();

    /**
     * 错误描述
     */
    String getMessage();
}
