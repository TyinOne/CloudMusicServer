package com.tyin.core.components.properties.models;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/4/22 1:42
 * @description ...
 */
@Data
public class TencentMapConfig {
    public static final String TYPE = "map";
    public static final String VERSION = "map_district_data_version";
    private String key;
    private String secretKey;
    private String mapApiHost;
    private String mapApiUri;
    private String mapDistrictDataVersion;
}
