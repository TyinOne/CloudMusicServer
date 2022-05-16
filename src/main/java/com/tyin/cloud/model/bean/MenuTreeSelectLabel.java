package com.tyin.cloud.model.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tyin.cloud.model.base.TreeBase;
import lombok.*;

/**
 * @author Tyin
 * @date 2022/5/16 16:57
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectLabel extends TreeBase {
    @JsonProperty("value")
    private Long id;
    @JsonIgnore
    private Long parentId;
    @JsonProperty("label")
    private String metaTitle;
    private Integer type;
}
