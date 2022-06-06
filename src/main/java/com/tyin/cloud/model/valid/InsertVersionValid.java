package com.tyin.cloud.model.valid;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/6/2 15:40
 * @description ...
 * MD5: "b6c0d205342252f62326bda133366e86"
 * forced: true
 * hash: "88a0f943de6faf4be5d5a144c32b3932e6bc7c4b5ce060def64abb20539f9d00"
 * name: "3de6f.zip"
 * src: "https://file.tyin.vip/tmp/1654498421384-4350865FD5114C21A5A5FB911E597D6A.zip"
 * updateLog: "更新xxx,更新xxx"
 * version: "1.0.0"
 */
@Data
public class InsertVersionValid {
    private String name;
    private String src;
    private String version;
    private String hash;
    private String md5;
    private String updateLog;
    private Boolean forced;
}
