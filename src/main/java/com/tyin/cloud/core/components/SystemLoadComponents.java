package com.tyin.cloud.core.components;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Tyin
 * @date 2022/3/29 21:50
 * @description ...
 */
@Component
@Slf4j
public class SystemLoadComponents {
    private Map<String, Map<String, String>> dict;
    @PostConstruct
    public void onLoad() {
        //加载字典


        log.info("System Start success!");
    }
}
