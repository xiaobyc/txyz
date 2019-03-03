package com.txyz.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * alex
 */
@Component(value = "redisUtils")
public class RedisUtils {
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

    /**
     * 保存
     *
     * @param key
     * @param value
     */
    public void set(final String key, final String value) {
        //this.set(key,value,null);
        //默认缓存30天
        this.set(key, value, 30 * 24 * 60 * 60L);
    }

    public void set(final String key, final String value, final Long liveTime) {
        try {
            redisTemplate.execute((RedisCallback) redisConnection -> {
                byte[] key_byte = redisTemplate.getStringSerializer().serialize(key);
                byte[] value_byte = redisTemplate.getStringSerializer().serialize(value);
                redisConnection.set(key_byte, value_byte);
                if (liveTime != null && liveTime > 0) {
                    redisConnection.expire(key_byte, liveTime);
                }
                return null;
            });
        } catch (Exception e) {
            LOGGER.error("redis存异常,key:" + key + ",value:" + value);
            e.printStackTrace();
            LOGGER.error("异常信息:" + e.getMessage());
        }
    }

    public String get(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                        byte[] key_byte = redisTemplate.getStringSerializer().serialize(key);
                        byte[] value = redisConnection.get(key_byte);
                        if (value == null) {
                            return null;
                        } else {
                            return redisTemplate.getStringSerializer().deserialize(value);
                        }
                    }
            );
        } catch (Exception e) {
            LOGGER.error("redis取值异常,key:" + key);
            return null;
        }
    }

    /**
     * 删除单个key的缓存
     *
     * @param key
     */
    public void del(final String key) {
        redisTemplate.execute((RedisCallback) redisConnection -> {
            byte[] key_byte = redisTemplate.getStringSerializer().serialize(key);
            redisConnection.del(key_byte);
            return null;
        });
    }

    /**
     * 根据key模糊删除多个
     *
     * @param pattern
     */
    public void delete(String pattern) {
        redisTemplate.delete(keys(pattern));
    }

    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public RedisTemplate<Serializable, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断否个key是否存在
     *
     * @param key
     * @return
     */
    public int exists(final String key) {
        return redisTemplate.execute((RedisCallback<Integer>) redisConnection -> {
            byte[] key_byte = redisTemplate.getStringSerializer().serialize(key);
            return redisConnection.exists(key_byte) ? 1 : 0;
        });
    }

    /**
     * @param key
     * @param filed
     * @param value
     * @param liveTime
     * @return
     */
    public Boolean hset(final String key, final String filed, final String value, final Long liveTime) {
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            boolean flag = redisConnection.hSet(serializer.serialize(key), serializer.serialize(filed), serializer.serialize(value));
            if (flag && liveTime != null && liveTime > 0) {
                flag = redisConnection.expire(serializer.serialize(key), liveTime);
            }
            return flag;
        });
    }

    /**
     * @param key
     * @param filed
     * @param value
     * @return
     */
    public Boolean hset(final String key, final String filed, final String value) {
        //默认缓存30天
        return this.hset(key, filed, value, 30 * 24 * 60 * 60L);
    }

    /**
     * @param key
     * @param filed
     * @return
     */
    public String hget(final String key, final String filed) {
        return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] value = redisConnection.hGet(serializer.serialize(key), serializer.serialize(filed));
            return serializer.deserialize(value);
        });
    }

    /**
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(final String key) {
        return redisTemplate.execute((RedisCallback<Map<String,String>>) redisConnection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Map<byte[], byte[]> all = redisConnection.hGetAll(serializer.serialize(key));
            Map<String, String> returnValue = new HashMap<>();
            for (Map.Entry<byte[], byte[]> entry : all.entrySet()) {
                returnValue.put(serializer.deserialize(entry.getKey()), serializer.deserialize(entry.getValue()));
            }
            return returnValue;
        });
    }
}
