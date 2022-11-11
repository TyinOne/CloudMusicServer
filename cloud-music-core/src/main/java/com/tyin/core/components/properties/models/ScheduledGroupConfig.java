package com.tyin.core.components.properties.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduledGroup {
        private String key;
        private String value;
    }
}
