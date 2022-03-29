package com.tyin.cloud.core.annotations;


import java.lang.annotation.*;

/**
 * @author Tyin
 * @date 2022/3/26 3:20
 * @description ...
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
}
