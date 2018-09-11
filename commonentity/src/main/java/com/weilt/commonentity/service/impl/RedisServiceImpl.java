package com.weilt.commonentity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weilt.commonentity.service.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void del(String key) {

    }

    @Override
    public void delAllByPrefix(String prefixKey) {

    }

    @Override
    public boolean exists(String key) {
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