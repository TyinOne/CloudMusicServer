package com.tyin.core.module.res.admin;


import com.tyin.core.module.base.TreeBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
