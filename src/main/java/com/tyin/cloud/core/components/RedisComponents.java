package com.tyin.cloud.core.components;

import com.tyin.cloud.core.utils.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Tyin
 * @date 2022/3/26 3:10
 * @description ...
 */
@Component
public class RedisComponents {
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }


    /**
     * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
     *
     * @param oldKey oldKey
     * @param newKey newKey
     */
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * newKey不存在时才重命名
     *
     * @param oldKey oldKey
     * @param newKey newKey
     * @return 修改成功返回true
     */
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return Boolean.TRUE.equals(redisTemplate.renameIfAbsent(oldKey, newKey));
    }

    /**
     * 删除key
     *
     * @param key key
     */
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除多个key
     *
     * @param keys keys
     */
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 删除Key的集合
     *
     * @param keys
     */
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    /**
     * 设置key的生命周期
     *
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     *
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 查询key的生命周期
     *
     * @param key
     * @param timeUnit
     * @return
     */
    public Long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 将key设置为永久有效
     *
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String get(String key) {
        return StringUtils.isBlank(key) ? "" : redisTemplate.opsForValue().get(key);
    }

    /**
     * key是否存在
     *
     * @param key key
     * @return true 存在
     */
    public boolean existsKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
