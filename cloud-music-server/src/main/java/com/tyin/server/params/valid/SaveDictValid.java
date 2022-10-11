package com.tyin.server.params.valid;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Tyin
 * @date 2022/5/12 1:06
 * @description ...
 */
@Data
public class SaveDictValid {
    @Schema(description = "字典ID")
    private Long id;
    @NotBlank
    @Schema(description = "字典分类")
    private String dictType;
    @NotBlank
    @Schema(description = "字典Key")
    private String dictKey;
    @NotBlank
    @Schema(description = "字典Value")
    private String dictValue;
    @Schema(description = "描述")
    private String dictDescription;
}
