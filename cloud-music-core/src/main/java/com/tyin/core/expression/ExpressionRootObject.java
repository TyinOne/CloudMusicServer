package com.tyin.core.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tyin
 * @date 2022/4/6 14:33
 * @description ...
 */
public record ExpressionRootObject(Object object, Object[] args) {
}
