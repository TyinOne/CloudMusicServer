package com.tyin.core.module.res.admin;

import com.tyin.core.module.base.TreeBase;
import lombok.*;

import java.util.List;
import java.util.Set;

/**
 * @author Tyin
 * @date 2022/5/5 16:29
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRouterListRes {

    private List<AdminRouterRes> list;
    private Set<String> permissions;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminRouterRes extends TreeBase {

        private String path;
        private String name;
        private String component;
        private String redirect;
        private MetaRes meta;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class MetaRes {
            private String icon;
            private String title;
            private String isLink;
            private Boolean isHide;
            private Boolean isKeepAlive;
            private Boolean isAffix;
            private Boolean isIframe;
            private Set<String> roles;
        }
    }
}