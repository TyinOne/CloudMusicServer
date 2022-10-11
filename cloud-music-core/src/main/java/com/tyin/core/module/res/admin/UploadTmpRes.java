package com.tyin.core.module.res.admin;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "临时文件URL")
    private String src;
    @Schema(description = "服务器文件URI")
    private String uri;
    @Schema(description = "文件名")
    private String fileName;
    @Schema(description = "md5")
    private String md5;
}
