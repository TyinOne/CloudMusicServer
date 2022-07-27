package com.tyin.core.utils.scheduled;

import com.tyin.core.module.entity.AdminScheduled;
import org.quartz.JobExecutionContext;

/**
 * @author Tyin
 * @date 2022/7/7 17:34
 * @description ...
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AdminScheduled sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
