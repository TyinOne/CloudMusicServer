package com.tyin.cloud.core.configs.properties.models;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author Tyin
 * @date 2022/4/6 10:03
 * @description ...
 */

@Data
@Component
public class OssProperties {
    private String ossFileHost;
    private String ossServerUri;
    private String ossFileUriTmp;
    private String ossFileUriImages;
}
