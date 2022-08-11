package com.tyin.core.module.base;

import java.util.Date;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/7/21 16:17
 * @description ...
 */
public interface TimerTaskState {
    /**
     * 获取任务key
     *
     * @return code
     */
    String getCode();

    /**
     * 过期时间
     *
     * @return 过期日期
     */
    Date getExpirationTime();

    /**
     * 任务所在组件名称
     *
     * @return 名称
     */
    String getComponentName();

    /**
     * 任务方法名称
     *
     * @return 名称
     */
    String getMethodName();

    /**
     * 任务执行方法参数
     *
     * @return 参数列表
     */
    List<Object[]> getMethodParams();
}
