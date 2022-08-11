package com.tyin.server.params.valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Tyin
 * @date 2022/5/12 1:06
 * @description ...
 */
@Data
public class SaveDictValid {
    @ApiModelProperty("字典ID")
    private Long id;
    @NotBlank
    @ApiModelProperty("字典分类")
    private String dictType;
    @NotBlank
    @ApiModelProperty("字典Key")
    private String dictKey;
    @NotBlank
    @ApiModelProperty("字典Value")
    private String dictValue;
    @ApiModelProperty("描述")
    private String dictDescription;
}
