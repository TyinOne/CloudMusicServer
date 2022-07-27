package com.tyin.core.utils.scheduled;

import com.tyin.core.module.entity.AdminScheduled;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * @author Tyin
 * @date 2022/7/14 9:32
 * @description ...
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AdminScheduled adminScheduled) throws Exception {
        JobInvokeUtil.invokeMethod(adminScheduled);
    }
}
