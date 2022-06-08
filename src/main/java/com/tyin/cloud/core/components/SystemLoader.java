package com.tyin.cloud.core.components;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.configs.properties.models.OssConfig;
import com.tyin.cloud.core.configs.properties.models.TencentMapConfig;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.entity.AdminDict;
import com.tyin.cloud.repository.admin.AdminDictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tyin.cloud.core.constants.RedisKeyConstants.OSS_PROPERTIES;
import static com.tyin.cloud.core.constants.RedisKeyConstants.TENCENT_MAP_PROPERTIES;

/**
 * @author Tyin
 * @date 2022/5/22 5:36
 * @description ...
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SystemLoader {
    private final PropertiesComponents propertiesComponents;
    private final AdminDictRepository adminDictRepository;
    private final RedisComponents redisComponents;

    @Async
    public void initOss() {
        log.info("oss is loading!");
        OssConfig ossConfig = new OssConfig();
        List<AdminDict> oss = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "oss"));
        ossConfig.setOssFileHost(oss.stream().filter(i -> StringUtils.equals("oss_file_host", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssServerUri(oss.stream().filter(i -> StringUtils.equals("oss_server_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileUriTmp(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_tmp", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileUriImages(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_images", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFileHotDownloads(oss.stream().filter(i -> StringUtils.equals("oss_update_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossConfig.setOssFilePackageDownloads(oss.stream().filter(i -> StringUtils.equals("oss_package_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        redisComponents.saveAsync(OSS_PROPERTIES, JsonUtils.toJSONString(ossConfig));
        propertiesComponents.setOss(ossConfig);
    }
    @Async
    public void initMap() {
        log.info("tencentMap is loading!");
        TencentMapConfig tencentMapConfig = new TencentMapConfig();
        List<AdminDict> oss = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "map"));
        tencentMapConfig.setKey(oss.stream().filter(i -> StringUtils.equals("key", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setSecretKey(oss.stream().filter(i -> StringUtils.equals("secret_key", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapApiHost(oss.stream().filter(i -> StringUtils.equals("map_api_host", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapApiUri(oss.stream().filter(i -> StringUtils.equals("map_api_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        tencentMapConfig.setMapDistrictDataVersion(oss.stream().filter(i -> StringUtils.equals("map_district_data_version", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        redisComponents.saveAsync(TENCENT_MAP_PROPERTIES, JsonUtils.toJSONString(tencentMapConfig));
        propertiesComponents.setTencentMap(tencentMapConfig);
    }
}
