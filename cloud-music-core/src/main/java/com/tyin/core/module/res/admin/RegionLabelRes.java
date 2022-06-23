package com.tyin.core.module.res.admin;

import com.tyin.core.module.bean.RegionLabel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/7 13:50
 * @description ...
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionLabelRes {
    private List<RegionLabel> list;
}
