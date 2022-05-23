package com.tyin.cloud.core.annotations;

import java.lang.annotation.*;

/**
 * @author Tyin
 * @date 2022/3/26 3:22
 * @description ...
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Open {
}
