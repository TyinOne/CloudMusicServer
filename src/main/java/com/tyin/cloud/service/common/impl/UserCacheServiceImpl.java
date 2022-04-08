package com.tyin.cloud.service.common.impl;

import com.tyin.cloud.core.utils.JsonUtils;
import com.tyin.cloud.service.common.IUserCacheService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/3/29 23:19
 * @description ...
 */
@Service
public class UserCacheServiceImpl implements IUserCacheService {

    @Override
    public <T> T getUserCache(String userCache, Class<T> parameterType) {
        return Objects.isNull(userCache) ? null : JsonUtils.toJavaObject(userCache, parameterType);
    }
}
