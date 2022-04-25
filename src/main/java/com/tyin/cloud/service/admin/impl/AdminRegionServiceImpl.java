package com.tyin.cloud.service.admin.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tyin.cloud.core.components.HttpComponents;
import com.tyin.cloud.core.configs.api.res.TencentMapDistrictRes;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.utils.Asserts;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.entity.AdminRegion;
import com.tyin.cloud.repository.admin.AdminRegionRepository;
import com.tyin.cloud.service.admin.IAdminRegionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tyin.cloud.core.constants.SDKConstants.DISTRICT_API;

/**
 * @author Tyin
 * @date 2022/4/22 13:56
 * @description ...
 */
@Service
@RequiredArgsConstructor
public class AdminRegionServiceImpl extends ServiceImpl<AdminRegionRepository, AdminRegion> implements IAdminRegionService {
    private final HttpComponents httpComponents;
    private final PropertiesComponents propertiesComponents;

    @Override
    @Async
    public void getAreaForTencent() {
        String key = propertiesComponents.getTencentMapKey();
        String secretKey = propertiesComponents.getTencentSecretKey();
        Map<String, String> params = Maps.newHashMap();
        params.put("key", key);
        params.put("sig", DigestUtils.md5Hex(("/ws/district/v1/list?key=" + key + secretKey).getBytes(StandardCharsets.UTF_8)));
        String str = httpComponents.doGet(DISTRICT_API, params);
        TencentMapDistrictRes res = JsonUtils.toJavaObject(str.replaceAll("\r|\n*", "").replaceAll("\\s", ""), TencentMapDistrictRes.class);
        Asserts.isTrue(Objects.nonNull(res) && Objects.nonNull(res.getResult()), Objects.isNull(Objects.requireNonNull(res).getMessage()) ? "" : res.getMessage());
        List<List<TencentMapDistrictRes.DistrictResultItem>> result = res.getResult();
        //省市区
        List<TencentMapDistrictRes.DistrictResultItem> provinces = result.get(0);
        List<TencentMapDistrictRes.DistrictResultItem> city = result.get(1);
        List<TencentMapDistrictRes.DistrictResultItem> town = result.get(2);
        for (TencentMapDistrictRes.DistrictResultItem item : city) {
            if (Objects.nonNull(item.getCidx()) && item.getCidx().size() > 0) {
                List<TencentMapDistrictRes.DistrictResultItem> districtResultItems = town.subList(item.getCidx().get(0), item.getCidx().get(1) + 1);
                for (TencentMapDistrictRes.DistrictResultItem i : districtResultItems) {
                    i.setParentId(item.getId());
                    i.setLevel(3);
                }
                item.setLevel(2);
                item.setChildren(districtResultItems);
            }
        }
        for (TencentMapDistrictRes.DistrictResultItem item : provinces) {
            if (Objects.nonNull(item.getCidx()) && item.getCidx().size() > 0) {
                List<TencentMapDistrictRes.DistrictResultItem> districtResultItems = city.subList(item.getCidx().get(0), item.getCidx().get(1) + 1);
                for (TencentMapDistrictRes.DistrictResultItem i : districtResultItems) {
                    i.setParentId(item.getId());
                    i.setLevel(2);
                }
                item.setChildren(districtResultItems);
                item.setLevel(1);
            }
        }
        List<AdminRegion> list = Lists.newArrayList();
        for (TencentMapDistrictRes.DistrictResultItem p : provinces) {
            List<TencentMapDistrictRes.DistrictResultItem> cs = p.getChildren();
            if (Objects.nonNull(cs)) {
                for (TencentMapDistrictRes.DistrictResultItem c : cs) {
                    List<TencentMapDistrictRes.DistrictResultItem> ts = c.getChildren();
                    if (Objects.nonNull(ts)) {
                        for (TencentMapDistrictRes.DistrictResultItem t : ts) {
                            list.add(AdminRegion.builder()
                                    .id(Long.valueOf(t.getId()))
                                    .parentId(Long.valueOf(t.getParentId()))
                                    .name(t.getName())
                                    .fullName(t.getFullname())
                                    .level(t.getLevel())
                                    .lat(t.getLocation().getLat())
                                    .lng(t.getLocation().getLng())
                                    .build());
                        }
                    }
                    list.add(AdminRegion.builder()
                            .id(Long.valueOf(c.getId()))
                            .parentId(Long.valueOf(c.getParentId()))
                            .name(c.getName())
                            .fullName(c.getFullname())
                            .level(c.getLevel())
                            .lat(c.getLocation().getLat())
                            .lng(c.getLocation().getLng())
                            .build());
                }
            }
            list.add(AdminRegion.builder()
                    .id(Long.valueOf(p.getId()))
                    .parentId(0L)
                    .name(p.getName())
                    .fullName(p.getFullname())
                    .level(p.getLevel())
                    .lat(p.getLocation().getLat())
                    .lng(p.getLocation().getLng())
                    .build());
        }
        Asserts.isTrue(this.saveOrUpdateBatch(list), "同步数据失败");
    }
}
