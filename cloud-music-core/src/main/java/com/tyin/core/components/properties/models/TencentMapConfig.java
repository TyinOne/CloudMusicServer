package com.tyin.core.components.properties.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("secret_key")
    private String secretKey;
    @JsonProperty("map_api_host")
    private String mapApiHost;
    @JsonProperty("map_api_uri")
    private String mapApiUri;
    @JsonProperty("map_district_data_version")
    private String mapDistrictDataVersion;
}
