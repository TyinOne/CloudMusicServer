package com.tyin.core.utils.scheduled;

import org.quartz.JobExecutionContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Tyin
 * @date 2022/7/7 17:34
 * @description ...
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, String invokeTarget) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        JobInvokeUtils.invokeMethod(invokeTarget);
    }
}
