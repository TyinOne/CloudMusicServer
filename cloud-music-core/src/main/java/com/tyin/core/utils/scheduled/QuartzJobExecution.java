package com.tyin.core.utils.scheduled;

import org.quartz.JobExecutionContext;

/**
 * @author Tyin
 * @date 2022/7/7 17:34
 * @description ...
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, String invokeTarget) throws Exception {
        JobInvokeUtils.invokeMethod(invokeTarget);
    }
}
