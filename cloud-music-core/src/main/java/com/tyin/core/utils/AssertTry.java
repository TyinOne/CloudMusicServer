package com.tyin.core.utils;

import org.quartz.SchedulerException;

/**
 * @author Tyin
 * @date 2022/11/10 12:19
 * @description ...
 */
@FunctionalInterface
public interface AssertTry {

    void trycatch() throws Exception;
}
