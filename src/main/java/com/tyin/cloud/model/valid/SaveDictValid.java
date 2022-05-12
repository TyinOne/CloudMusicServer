package com.tyin.cloud.model.valid;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Tyin
 * @date 2022/5/12 1:06
 * @description ...
 */
@Data
public class SaveDictValid {
    private Long id;
    @NotBlank
    private String dictType;
    @NotBlank
    private String dictKey;
    @NotBlank
    private String dictValue;
    private String dictDescription;
}
