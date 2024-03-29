package com.tyin.core.utils.scheduled;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Tyin
 * @date 2022/7/14 9:32
 * @description ...
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, String invokeTarget) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        JobInvokeUtils.invokeMethod(invokeTarget);
    }
}
