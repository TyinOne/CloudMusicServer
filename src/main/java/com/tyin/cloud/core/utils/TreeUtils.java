package com.tyin.cloud.core.utils;

import com.tyin.cloud.model.base.TreeBase;

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
    public static List<? extends TreeBase> buildTree(List<? extends TreeBase> entities, Long root) {
        return entities.stream().filter(categoryEntity ->
                categoryEntity.getParentId().equals(root)
        ).peek((menu) -> menu.setChildren(getChildren(menu, entities))).sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort()))).collect(Collectors.toList());
    }
    private static List<? extends TreeBase> getChildren(TreeBase root, List<? extends TreeBase> list) {
        return list.stream().filter(i -> {
            return Objects.equals(i.getParentId(), root.getId());
        }).peek(i -> {
            i.setChildren(getChildren(i, list));
        }).sorted((menu1, menu2) -> {
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu1.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
    }
}
