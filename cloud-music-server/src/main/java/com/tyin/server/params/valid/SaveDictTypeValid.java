package com.tyin.server.params.valid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/6/9 14:30
 * @description ...
 */
@Data
public class SaveDictTypeValid {
    private Long id;
    @Schema(description = "字典分类")
    private String dictType;
    @Schema(description = "字典名称")
    private String dictLabel;
    @Schema(description = "描述")
    private String dictDescription;
}
