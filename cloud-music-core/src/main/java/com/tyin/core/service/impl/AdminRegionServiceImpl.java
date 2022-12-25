package com.tyin.core.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tyin.core.components.HttpComponents;
import com.tyin.core.components.PropertiesComponents;
import com.tyin.core.components.properties.PropertiesEnum;
import com.tyin.core.module.bean.RegionLabel;
import com.tyin.core.module.entity.AdminRegion;
import com.tyin.core.module.res.admin.AdminRegionRes;
import com.tyin.core.module.res.admin.TencentMapDistrictRes;
import com.tyin.core.repository.admin.AdminRegionRepository;
import com.tyin.core.service.IAdminDictService;
import com.tyin.core.service.IAdminRegionService;
import com.tyin.core.utils.Asserts;
import com.tyin.core.utils.JsonUtils;
import com.tyin.core.utils.StringUtils;
import com.tyin.core.utils.TreeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tyin.core.components.properties.models.TencentMapConfig.TYPE;
import static com.tyin.core.components.properties.models.TencentMapConfig.VERSION;
import static com.tyin.core.constants.PropertiesKeyConstants.*;

/**
 * @author Tyin
 * @date 2022/4/22 13:56
 * @description ...
 */
@SuppressWarnings("ALL")
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AdminRegionServiceImpl extends ServiceImpl<AdminRegionRepository, AdminRegion> implements IAdminRegionService {
    private final HttpComponents httpComponents;
    private final PropertiesComponents propertiesComponents;
    private final AdminRegionRepository adminRegionRepository;
    private final IAdminDictService adminDictService;

    @Override
    @Async
    public void getAreaForTencent() {
        String apiUrl = propertiesComponents.getConfigByKey(PropertiesEnum.MAP, MAP_API_URI),
                apiHost = propertiesComponents.getConfigByKey(PropertiesEnum.MAP, MAP_API_HOST),
                key = propertiesComponents.getConfigByKey(PropertiesEnum.MAP, KEY),
                secretKey = propertiesComponents.getConfigByKey(PropertiesEnum.MAP, SECRET_KEY),
                version = propertiesComponents.getConfigByKey(PropertiesEnum.MAP, VERSION);
        Map<String, String> params = Maps.newHashMap();
        params.put("key", key);
        params.put("sig", DigestUtils.md5Hex((apiUrl + "?key=" + key + secretKey).getBytes(StandardCharsets.UTF_8)));
        String str = httpComponents.doGet(apiHost + apiUrl, params);
        TencentMapDistrictRes res = JsonUtils.toJavaObject(str.replaceAll("\r|\n*", "").replaceAll("\\s", ""), TencentMapDistrictRes.class);
        Asserts.isTrue(Objects.nonNull(res) && Objects.nonNull(res.getResult()), Objects.isNull(Objects.requireNonNull(res).getMessage()) ? "" : res.getMessage());
        //检查更新
        String dataVersion = res.getDataVersion();
        Asserts.isTrue(!dataVersion.equals(version), "暂无更新！");
        adminDictService.updateValueBy(TYPE, VERSION, dataVersion);

        //TODO updaDICT
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

    @Override
    public List<RegionLabel> getRegionLabel(Long rootId) {
        List<RegionLabel> list = adminRegionRepository.selectLabel(Wrappers.<AdminRegion>lambdaQuery().ge(AdminRegion::getParentId, rootId));
        return TreeUtils.buildTree(list, rootId, false);
    }

    @Override
    public List<AdminRegionRes> selectListBy(Long parentId, String keywords, Integer level) {
        return adminRegionRepository.selectResList(Wrappers.<AdminRegion>lambdaQuery()
                .eq(StringUtils.isEmpty(keywords), AdminRegion::getParentId, parentId)
                .eq(level != 0, AdminRegion::getLevel, level)
                .and(StringUtils.isNotEmpty(keywords), i -> i
                        .or().apply("INSTR(`name`, {0}) > 0", keywords)
                        .or().apply("INSTR(`full_name`, {0}) > 0", keywords))
        );
    }
}
