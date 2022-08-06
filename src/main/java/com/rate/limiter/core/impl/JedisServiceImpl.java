package com.rate.limiter.core.impl;

import com.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

public class JedisServiceImpl implements JedisService {

    private final String redisHost;
    private final Integer redisPort;
    private final Integer redisDb;
    private JedisPool jedisPool;

    public JedisServiceImpl(String redisHost, Integer redisPort, Integer redisDb) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.redisDb = redisDb;
    }

    public JedisPool getJedisPool() {
        if (Objects.isNull(jedisPool)) {
            synchronized (JedisService.class) {
                if (Objects.isNull(jedisPool)) {
                    this.jedisPool = new JedisPool(redisHost, redisPort);
                }
            }
        }
        return this.jedisPool;
    }
}
