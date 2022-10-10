package com.tyin.core.components.properties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/9/28 11:20
 * @description ...
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseConfig {
    private String type;
    private String key;
    private String value;
}
