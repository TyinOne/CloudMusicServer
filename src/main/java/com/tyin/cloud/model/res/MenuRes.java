package com.tyin.cloud.model.res;

import com.tyin.cloud.model.base.TreeBase;
import lombok.*;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/11 22:05
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRes {
    private List<? extends TreeBase> list;

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class MenuItem extends TreeBase {
        private String name;
        private String path;
        private String component;
        private String metaIcons;
        private String metaTitle;
        private String security;
        private Integer sort;
        private Integer type;
    }
}
