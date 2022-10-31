package com.tyin.core.components.properties.models;

import lombok.Data;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/10/31 18:56
 * @description ...
 */
@Data
public class ScheduledGroupConfig {
    private List<ScheduledGroup> list;

    @Data
    public static class ScheduledGroup {
        private String key;
        private String value;
    }
}
