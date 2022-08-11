package com.tyin.server.service;

/**
 * @author Tyin
 * @date 2022/3/29 23:19
 * @description ...
 */
public interface IUserCacheService {
    <T> T getUserCache(String userCache, Class<T> parameterType);
}
