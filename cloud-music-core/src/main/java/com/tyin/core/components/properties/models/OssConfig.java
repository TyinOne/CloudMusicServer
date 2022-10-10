package com.tyin.core.components.properties.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/4/6 10:03
 * @description ...
 */

@Data
public class OssConfig {
    @JsonProperty("oss_file_host")
    private String ossFileHost;
    @JsonProperty("oss_server_uri")
    private String ossServerUri;
    @JsonProperty("oss_file_uri_temp")
    private String ossFileUriTmp;
    @JsonProperty("oss_file_uri_images")
    private String ossFileUriImages;
    @JsonProperty("oss_hot_downloads")
    private String ossHotDownloads;
    @JsonProperty("oss_package_uri")
    private String ossPackageUri;
}
