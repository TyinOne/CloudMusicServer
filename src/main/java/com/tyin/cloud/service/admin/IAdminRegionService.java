package com.tyin.cloud.service.admin;

import com.tyin.cloud.model.bean.RegionLabel;
import com.tyin.cloud.model.res.AdminRegionRes;

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
