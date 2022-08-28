package io.github.kidminks.rate.limiter.core.factory;

import io.github.kidminks.rate.limiter.core.impl.JedisServiceImpl;
import io.github.kidminks.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.JedisPoolConfig;

public interface JedisFactory {
    static JedisService getJedisService(String redisHost, Integer redisPort, Integer redisDb, JedisPoolConfig jedisPoolConfig) {
        return new JedisServiceImpl(redisHost, redisPort, redisDb, jedisPoolConfig);
    }
}
