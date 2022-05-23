package com.tyin.cloud.core.annotations;

import java.lang.annotation.*;

/**
 * @author Tyin
 * @date 2022/5/10 23:45
 * @description ...
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoLog {
}
