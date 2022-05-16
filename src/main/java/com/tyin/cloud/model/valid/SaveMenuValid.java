package com.tyin.cloud.model.valid;

import lombok.Data;

/**
 * @author Tyin
 * @date 2022/5/17 0:33
 * @description ...
 */
@Data
public class SaveMenuValid {
    private Long id;
    private Long parentId;
    private Integer sort;

    private String name;
    private String path;
    private String redirect;
    private String component;
    private Integer type;
    private String security;

    private String metaTitle;
    private String metaIcons;
    private String metaIsLink;
    private String metaRoles;
    private Boolean metaIsHide;
    private Boolean metaIsAffix;
    private Boolean metaIsIframe;
    private Boolean metaIsKeepAlive;

    private Boolean disabled;
}
