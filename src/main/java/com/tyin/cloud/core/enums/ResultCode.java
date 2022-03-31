package com.tyin.cloud.core.enums;

import com.tyin.cloud.core.exception.handle.BaseErrorInfoInterface;

/**
 * @author Tyin
 * @date 2022/3/26 2:50
 * @description ...
 */
public enum ResultCode implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS(200, "ok"),
    FAIL(400, "fail"),
    SIGNATURE_NOT_MATCH(403, "token invalid"),
    NOT_FOUND(404, "not found"),
    INTERNAL_SERVER_ERROR(500, "server error"),
    SERVER_BUSY(503, "server busyness");
    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ResultCode(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public Integer getCode() {
        return resultCode;
    }

    @Override
    public String getMessage() {
        return resultMsg;
    }
}
