package com.tyin.cloud.model.res;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/6/6 9:28
 * @description ...
 */
@Data
public class UpdateJsonUploadRes {
    private String version;
    private String hash;
    private String name;
}
