package com.tyin.cloud.core.configs.properties.models;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/4/6 10:03
 * @description ...
 */

@Data
public class OssConfig {
    private String ossFileHost;
    private String ossServerUri;
    private String ossFileUriTmp;
    private String ossFileUriImages;
    private String ossFileHotDownloads;
    private String ossFilePackageDownloads;
}
