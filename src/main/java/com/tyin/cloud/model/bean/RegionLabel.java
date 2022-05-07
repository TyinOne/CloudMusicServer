package com.tyin.cloud.model.bean;

import com.tyin.cloud.model.base.TreeBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Tyin
 * @date 2022/5/7 13:44
 * @description ...
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RegionLabel extends TreeBase {
    private String value;
    private String label;
}
