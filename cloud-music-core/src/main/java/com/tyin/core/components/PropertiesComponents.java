package com.tyin.core.components;

import com.tyin.core.components.properties.PropertiesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.tyin.core.constants.RedisKeyConstants.SYSTEM_CONFIG;

/**
 * @author Tyin
 * @date 2022/3/29 17:37
 * @description ...
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class PropertiesComponents {
    private final RedisComponents redisComponents;

    public String getConfigByKey(PropertiesEnum propertiesEnum, String key) {
        return redisComponents.get(SYSTEM_CONFIG + propertiesEnum.getType() + ":" + key);
    }
}
