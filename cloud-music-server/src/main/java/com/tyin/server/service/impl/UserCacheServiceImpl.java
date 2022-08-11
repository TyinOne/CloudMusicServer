package com.tyin.server.service.impl;


import com.tyin.core.utils.JsonUtils;
import com.tyin.server.service.IUserCacheService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Tyin
 * @date 2022/3/29 23:19
 * @description ...
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserCacheServiceImpl implements IUserCacheService {

    @Override
    public <T> T getUserCache(String userCache, Class<T> parameterType) {
        return Objects.isNull(userCache) ? null : JsonUtils.toJavaObject(userCache, parameterType);
    }
}
