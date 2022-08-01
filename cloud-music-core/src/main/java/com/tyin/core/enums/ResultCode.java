package com.tyin.core.enums;


import com.tyin.core.exception.handle.BaseErrorInfoInterface;

/**
 * @author Tyin
 * @date 2022/3/26 2:50
 * @description ...
 */
public enum ResultCode implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS(200, "成功!"),
    FAIL(400, "失败!"),
    PERMISSION_DENIED(402, "权限不足，无法访问!"),
    SIGNATURE_NOT_MATCH(403, "认证失败, 请重新登录!"),
    NOT_FOUND(404, "未找到资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器异常!"),
    SERVER_BUSY(503, "服务器繁忙!");
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
