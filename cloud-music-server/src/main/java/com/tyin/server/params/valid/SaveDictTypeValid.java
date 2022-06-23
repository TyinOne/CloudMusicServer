package com.tyin.server.params.valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/6/9 14:30
 * @description ...
 */
@Data
public class SaveDictTypeValid {
    private Long id;
    @ApiModelProperty("字典分类")
    private String dictType;
    @ApiModelProperty("字典名称")
    private String dictLabel;
    @ApiModelProperty("描述")
    private String dictDescription;
}
