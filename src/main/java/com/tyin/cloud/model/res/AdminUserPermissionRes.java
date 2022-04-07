package com.tyin.cloud.model.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/3/31 17:09
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserPermissionRes {
    /**
     * 用户所有菜单
     */
    List<RouterRes> routerRes;


    @Data
    public static class RouterRes {
        private Long id;
        private String name;
        private String path;
        private String redirect;
        private String component;

        private String metaTitle;
        private String metaIcons;
        private String metaIsLink;
        private String metaRoles;
        private Boolean metaIsHide;
        private Boolean metaIsAffix;
        private Boolean metaIsIframe;
        private Boolean metaIsKeepAlive;


        private List<RouterRes> children;
    }
}
