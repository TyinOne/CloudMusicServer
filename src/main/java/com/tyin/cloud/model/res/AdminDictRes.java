package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tyin
 * @date 2022/5/8 23:41
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDictRes {
    private Long id;
    private String dictType;
    private String dictLabel;
    private String dictKey;
    private String dictValue;
    private String dictDescription;

}
