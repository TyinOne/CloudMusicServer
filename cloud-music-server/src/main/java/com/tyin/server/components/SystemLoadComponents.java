package com.tyin.server.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Tyin
 * @date 2022/3/29 21:50
 * @description ...
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemLoadComponents {
    private final SystemLoader systemLoader;

    @PostConstruct
    public void onLoad() {
        //加载字典
        systemLoader.initOss();
        systemLoader.initMap();
        log.info("System Start success!");
    }

}
