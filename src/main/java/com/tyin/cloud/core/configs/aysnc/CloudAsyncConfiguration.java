package com.tyin.cloud.core.configs.aysnc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Tyin
 * @date 2022/3/29 16:11
 * @description ...
 */
@Configuration
@Slf4j
public class CloudAsyncConfiguration implements AsyncConfigurer {

    @Bean(name = "CloudAsyncExecutor")
    public ThreadPoolTaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = 10;
        executor.setCorePoolSize(corePoolSize);
        int maxPoolSize = 50;
        executor.setMaxPoolSize(maxPoolSize);
        int queueCapacity = 10;
        executor.setQueueCapacity(queueCapacity);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        String threadNamePrefix = "CloudAsyncExecutor-";
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //使用自定义的跨线程的请求级别线程工厂类19
        int awaitTerminationSeconds = 5;
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.initialize();
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return executor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.warn(String.format("异步任务异常：%s", method.getName()), ex);
        };
    }
}
