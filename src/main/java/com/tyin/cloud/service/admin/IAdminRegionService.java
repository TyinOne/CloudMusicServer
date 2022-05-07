package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.bean.RegionLabel;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/22 13:56
 * @description ...
 */
public interface IAdminRegionService {
    void getAreaForTencent();

    List<RegionLabel> getRegionLabel(Long rootId);
}
