package com.rate.limiter.core.impl;

import com.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
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

    private JedisPool getJedisPool() {
        if (Objects.isNull(jedisPool)) {
            synchronized (JedisService.class) {
                if (Objects.isNull(jedisPool)) {
                    this.jedisPool = new JedisPool(redisHost, redisPort);
                }
            }
        }
        return this.jedisPool;
    }

    @Override
    public void setHashSet(String key, HashMap<String, String> data) {
        Jedis jedis = jedisPool.getResource();
        jedis.hmset(key, data);
    }

    @Override
    public HashMap<String, String> getHashSet(String key) {
        Jedis jedis = jedisPool.getResource();
        return (HashMap<String, String>) jedis.hgetAll(key);
    }

    @Override
    public String runLua(String lua, List<String> keys, List<String> args) {
        Jedis jedis = jedisPool.getResource();
        String luaSHA = jedis.scriptLoad(lua);
        jedis.eval(luaSHA, keys, args);
        return null;
    }
}
