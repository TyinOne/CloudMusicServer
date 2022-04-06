package com.tyin.cloud.core.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tyin
 * @date 2022/4/6 14:33
 * @description ...
 */
@AllArgsConstructor
@Getter
public class ExpressionRootObject {
    private final Object object;
    private final Object[] args;
}
