package com.tyin.core.utils;


import com.tyin.core.module.base.TreeBase;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Tyin
 * @date 2022/4/11 22:09
 * @description ...
 */
public class TreeUtils {

    /**
     * 妙啊
     *
     * @param entities 需要转换的List
     * @param root     根节点id
     * @param sort     是否需要排序
     * @param <T>      树形泛型 extends TreeBase
     * @return entities树形结构
     */
    public static <T extends TreeBase> List<T> buildTree(List<T> entities, Long root, Boolean sort) {
        return entities.stream()
                .filter(categoryEntity -> categoryEntity.getParentId().equals(root))
                .peek(menu -> menu.setChildren(getChildren(menu, entities, sort)))
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }

    private static <T extends TreeBase> List<T> getChildren(T root, List<T> list, Boolean sort) {
        return list.stream()
                .filter(i -> Objects.equals(i.getParentId(), root.getId()))
                .peek(i -> i.setChildren(getChildren(i, list, sort)))
                .sorted((i, j) -> sort ? (Objects.isNull(i.getSort()) ? 0 : i.getSort()) - (Objects.isNull(i.getSort()) ? 0 : j.getSort()) : 0)
                .collect(Collectors.toList());
    }
}
