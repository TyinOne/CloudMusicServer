package com.tyin.server.components;

import com.tyin.server.loader.SystemLoader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;

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
        try {
            systemLoader.initDict();
            systemLoader.startTimerTask();
            log.info("System Start success!");
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                throw new ClassNotFoundException("Hey guy,I need Crazy Thursday & VMe 50$");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
