package com.tyin.cloud.model.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tyin.cloud.model.base.TreeBase;
import lombok.*;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/9 23:42
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuLabelRes {
    private List<? extends TreeBase> list;
    private List<Long> selected;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuData extends TreeBase {
        private Long id;
        @JsonIgnore
        private Long parentId;
        @JsonProperty("label")
        private String metaTitle;
    }
}
