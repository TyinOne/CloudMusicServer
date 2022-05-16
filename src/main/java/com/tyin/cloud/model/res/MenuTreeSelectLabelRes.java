package com.tyin.cloud.model.res;

import com.tyin.cloud.model.base.TreeBase;
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
