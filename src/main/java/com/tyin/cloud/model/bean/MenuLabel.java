package com.tyin.cloud.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tyin.cloud.model.base.TreeBase;
import lombok.*;

/**
 * @author Tyin
 * @date 2022/5/7 13:50
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuLabel extends TreeBase {
    private Long id;
    @JsonIgnore
    private Long parentId;
    @JsonProperty("label")
    private String metaTitle;
}
