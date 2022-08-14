package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.JedisServiceImpl;
import com.rate.limiter.core.inter.JedisService;

public interface JedisFactory {
    static JedisService getJedisService(String redisHost, Integer redisPort, Integer redisDb) {
        return new JedisServiceImpl(redisHost, redisPort, redisDb);
    }
}
