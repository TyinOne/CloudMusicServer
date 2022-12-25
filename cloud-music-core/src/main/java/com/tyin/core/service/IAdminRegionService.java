package com.tyin.core.service;


import com.tyin.core.module.bean.RegionLabel;
import com.tyin.core.module.res.admin.AdminRegionRes;

import java.util.List;

/**
 * @author Tyin
 * @date 2022/4/22 13:56
 * @description ...
 */
public interface IAdminRegionService {
    void getAreaForTencent();

    List<RegionLabel> getRegionLabel(Long rootId);

    List<AdminRegionRes> selectListBy(Long parentId, String keywords, Integer level);
}
