package com.tyin.server.components.scheduled;

import com.tyin.core.components.RedisComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/11/8 17:40
 * @description 系统任务组
 */
@Component("systemScheduled")
@RequiredArgsConstructor
public class SystemScheduledComponents {
    private final RedisComponents redisComponents;

    public void aliveRedis() {
        redisComponents.get("Scheduled");
    }
}
