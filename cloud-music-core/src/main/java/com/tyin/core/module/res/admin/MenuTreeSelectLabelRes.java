package com.tyin.core.module.res.admin;


import com.tyin.core.module.base.TreeBase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/16 16:55
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuTreeSelectLabelRes {
    private List<? extends TreeBase> list;
}
