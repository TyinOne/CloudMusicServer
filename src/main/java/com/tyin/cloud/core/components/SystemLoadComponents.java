package com.tyin.cloud.core.components;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tyin.cloud.core.configs.properties.PropertiesComponents;
import com.tyin.cloud.core.configs.properties.models.OssProperties;
import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.model.entity.AdminDict;
import com.tyin.cloud.repository.admin.AdminDictRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.tyin.cloud.core.constants.RedisKeyConstants.OSS_PROPERTIES;

/**
 * @author Tyin
 * @date 2022/3/29 21:50
 * @description ...
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SystemLoadComponents {
    private final AdminDictRepository adminDictRepository;
    private final PropertiesComponents propertiesComponents;
    private final RedisComponents redisComponents;

    @PostConstruct
    public void onLoad() {
        //加载字典
        propertiesComponents.setOss(initOss());

        log.info("System Start success!");
    }

    public OssProperties initOss() {
        if (redisComponents.existsKey(OSS_PROPERTIES)) {
            String ossStr = redisComponents.get(OSS_PROPERTIES);
            return JsonUtils.toJavaObject(ossStr, OssProperties.class);
        }
        List<AdminDict> oss = adminDictRepository.selectList(Wrappers.<AdminDict>lambdaQuery().eq(AdminDict::getDictType, "oss"));
        OssProperties ossProperties = new OssProperties();
        ossProperties.setOssFileHost(oss.stream().filter(i -> StringUtils.equals("oss_file_host", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossProperties.setOssServerUri(oss.stream().filter(i -> StringUtils.equals("oss_server_uri", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossProperties.setOssFileUriTmp(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_tmp", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        ossProperties.setOssFileUriImages(oss.stream().filter(i -> StringUtils.equals("oss_file_uri_images", i.getDictKey())).map(AdminDict::getDictValue).findFirst().orElse(""));
        redisComponents.save(OSS_PROPERTIES, JsonUtils.toJSONString(ossProperties));
        return ossProperties;
    }
}
