package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/5/11 1:04
 * @description ...
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadTmpRes {
    private String src;
    private String uri;
    private String fileName;
}
