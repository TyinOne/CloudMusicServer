package com.tyin.cloud.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.tyin.cloud.model.base.BaseEntity;
import lombok.*;

import java.io.Serializable;

/**
 * @author Tyin
 * @date 2022/4/8 0:28
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminMenu extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
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
