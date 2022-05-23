package com.tyin.cloud.model.res;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("临时文件URL")
    private String src;
    @ApiModelProperty("服务器文件URI")
    private String uri;
    @ApiModelProperty("文件名")
    private String fileName;
}
