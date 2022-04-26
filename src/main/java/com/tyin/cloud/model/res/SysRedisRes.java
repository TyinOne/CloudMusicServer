package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Properties;

/**
 * @author Tyin
 * @date 2022/4/26 11:08
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysRedisRes {
    private Properties info;
    private List<Command> commandStats;
    private Long dbSize;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Command {
        private String name;
        private String value;
    }
}
