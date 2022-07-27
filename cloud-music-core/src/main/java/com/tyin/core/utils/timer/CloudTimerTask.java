package com.tyin.core.utils.timer;

import com.tyin.core.utils.InvokeUtils;
import com.tyin.core.utils.SpringUtils;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Tyin
 * @date 2022/7/21 11:48
 * @description ...
 */
@AllArgsConstructor
@Builder
public class CloudTimerTask implements TimerTask {
    private long time;
    private String code;
    private String componentName;
    private String methodName;
    private List<Object[]> methodParams;

    @Override
    public void run(Timeout timeout) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //执行目标方法
        Object cloudTimerTaskComponents = SpringUtils.getBean(this.componentName);
        InvokeUtils.invokeMethod(cloudTimerTaskComponents, this.methodName, InvokeUtils.getMethodParams("(" + "'" + code + "'" + ")"));
    }

    public Timeout start(Timer timer) {
        return timer.newTimeout(this, this.time, TimeUnit.MILLISECONDS);
    }
}
