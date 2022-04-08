package com.tyin.cloud.core.components;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/3/29 21:50
 * @description ...
 */
@Component
@Slf4j
public class SystemLoadComponents {

    @PostConstruct
    public void onLoad() {
        log.info("System Start INIT!");
    }
}
