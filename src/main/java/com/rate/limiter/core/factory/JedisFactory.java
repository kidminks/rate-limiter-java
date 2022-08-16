package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.JedisServiceImpl;
import com.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.JedisPoolConfig;

public interface JedisFactory {
    static JedisService getJedisService(String redisHost, Integer redisPort, Integer redisDb, JedisPoolConfig jedisPoolConfig) {
        return new JedisServiceImpl(redisHost, redisPort, redisDb, jedisPoolConfig);
    }
}
