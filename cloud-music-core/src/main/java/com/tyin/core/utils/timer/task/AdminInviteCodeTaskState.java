package com.tyin.core.utils.timer.task;

import com.tyin.core.module.base.TimerTaskState;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/7/21 16:31
 * @description 邀请码自动过期任务轮 执行方法配置
 */
public interface AdminInviteCodeTaskState extends TimerTaskState {
    String COMPONENT_NAME = "adminInviteCodeServiceImpl";
    String METHOD_NAME = "handleInviteCodeExpire";
    List<Object[]> METHOD_PARAMS = null;

    /**
     * 任务所在组件名称
     *
     * @return 名称
     */
    @Override
    default String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * 任务方法名称
     *
     * @return 名称
     */
    @Override
    default String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * 任务执行方法参数
     *
     * @return 参数列表
     */
    @Override
    default List<Object[]> getMethodParams() {
        return METHOD_PARAMS;
    }
}
