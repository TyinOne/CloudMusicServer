package com.tyin.core.components;

import com.google.common.collect.Maps;
import com.tyin.core.module.base.TimerTaskState;
import com.tyin.core.utils.timer.CloudTimerTask;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/7/21 11:57
 * @description ...
 */
@Component
@Slf4j
public class CloudTimerTaskComponents {
    private final Timer timer = new HashedWheelTimer();
    private final Map<String, Timeout> taskMap = Maps.newConcurrentMap();

    public void init(List<? extends TimerTaskState> list) {
        list.stream().filter(Objects::nonNull).forEach(i -> {
            Timeout start = CloudTimerTask.builder()
                    .code(i.getCode())
                    .time(i.getExpirationTime().getTime() - System.currentTimeMillis())
                    .componentName(i.getComponentName())
                    .methodName(i.getMethodName())
                    .methodParams(i.getMethodParams())
                    .build().start(timer);
            taskMap.put(i.getCode(), start);
        });
    }

    /**
     * 单个添加任务
     *
     * @param state 任务item
     */
    public void addTask(TimerTaskState state) {
        Timeout start = CloudTimerTask.builder()
                .code(state.getCode())
                .time(state.getExpirationTime().getTime() - System.currentTimeMillis())
                .componentName(state.getComponentName())
                .methodName(state.getMethodName())
                .methodParams(state.getMethodParams())
                .build().start(timer);
        taskMap.put(state.getCode(), start);
    }

    /**
     * 立即执行任务并删除
     *
     * @param code 任务key
     */
    public void remove(String code) {
        Timeout timeout = taskMap.get(code);
        taskMap.remove(code);
        timeout.cancel();
    }
}
