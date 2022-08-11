package com.tyin.core.module.base;

import lombok.Data;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/11 22:57
 * @description ...
 */
@Data
public class TreeBase {
    private Long id;
    private Long parentId;
    private Integer sort;
    private List<? extends TreeBase> children;
}
