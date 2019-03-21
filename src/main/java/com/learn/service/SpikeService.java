package com.learn.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

@Service
public class SpikeService {
    public static final String SKU_PREFIX = "sku";
    public static final String SKU_LIST_PREFIX = "buy:";
    // KEYS[1] String名 KEYS[2] 队列名 KEYS[3] userId
    private RedisScript<Long> buyScript = new DefaultRedisScript<>("if redis.call('get', KEYS[1]) == 0 \n " +
            "then return redis.call('get', KEYS[1]) \n " +
            "else return redis.call('get', KEYS[1]) end", Long.class);
    @Autowired
    RedisTemplate redisTemplate;
    public int buy(long sku, long userId){
        redisTemplate.execute(buyScript, Lists.newArrayList(SKU_PREFIX + ":" + sku));
        return 1;
    }
}
