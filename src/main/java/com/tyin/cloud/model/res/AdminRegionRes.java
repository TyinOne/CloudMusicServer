package com.tyin.cloud.model.res;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Tyin
 * @date 2022/5/22 13:55
 * @description ...
 */
@Data
public class AdminRegionRes {
    private Long pid;
    private Long id;
    private String name;
    private String fullName;
    private BigDecimal lat;
    private BigDecimal lng;
    private Integer level;
    private Boolean hasChildren = true;
    private List<AdminRegionRes> children = Lists.newArrayList();
}
