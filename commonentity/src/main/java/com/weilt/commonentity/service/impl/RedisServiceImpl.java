package com.weilt.commonentity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weilt.commonentity.service.IRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @author weilt
 * @com.weilt.common.redisservice.impl
 * @date 2018/8/22 == 17:31
 */
@Service
public class RedisServiceImpl implements IRedisService {
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    private RedisTemplate redisTemplate;
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, String value) {
           stringRedisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void del(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void delAllByPrefix(String prefixKey) {
        stringRedisTemplate.delete(prefixKey+"*");
    }

    @Override
    public boolean exists(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(value))
        {
            return true;
        }
        return false;
    }

    @Override
    public void setCacheProductIdByCategory(HashMap<String, Double> map) {

    }

    @Override
    public List<String> getCacheProductIdByCategoryId(Integer categoryId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public void delCacheListProductId(String key) {

    }
}