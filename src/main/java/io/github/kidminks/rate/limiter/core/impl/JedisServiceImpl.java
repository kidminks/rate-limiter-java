package io.github.kidminks.rate.limiter.core.impl;

import io.github.kidminks.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class JedisServiceImpl implements JedisService {

    private final String redisHost;
    private final Integer redisPort;
    private final Integer redisDb;
    private JedisPool jedisPool;
    private JedisPoolConfig jedisPoolConfig;

    public JedisServiceImpl(String redisHost, Integer redisPort, Integer redisDb, JedisPoolConfig jedisPoolConfig) {
        this.redisHost = redisHost;
        this.redisPort = redisPort;
        this.redisDb = redisDb;
        this.jedisPoolConfig = jedisPoolConfig;
    }

    private JedisPool getJedisPool() {
        if (Objects.isNull(jedisPool)) {
            synchronized (JedisService.class) {
                if (Objects.isNull(jedisPool)) {
                    this.jedisPool = new JedisPool(jedisPoolConfig, redisHost, redisPort);
                }
            }
        }
        return this.jedisPool;
    }

    /**
     * Function to run lua script and provide response from redis
     * this is done to avoid multiple call made to redis in case we need to check multiple things
     * @param lua
     * @param keys
     * @param args
     * @return
     */
    @Override
    public String runLua(String lua, List<String> keys, List<String> args) {
        Jedis jedis = getJedisPool().getResource();
        jedis.select(redisDb);
        String data = jedis.eval(lua, keys, args).toString();
        jedis.close();
        return data;
    }

    @Override
    public Long increment(String key, Long window) {
        Jedis jedis = getJedisPool().getResource();
        jedis.select(redisDb);
        Long data = jedis.incr(key);
        if (data.equals(1L)) {
            jedis.expire(key, window/1000);
        }
        jedis.close();
        return data;
    }

    @Override
    public void deleteKey(String key) {
        Jedis jedis = getJedisPool().getResource();
        jedis.select(redisDb);
        jedis.del(key);
        jedis.close();
    }

    @Override
    public void hashSet(String key, HashMap<String, String> data) {
        Jedis jedis = getJedisPool().getResource();
        jedis.select(redisDb);
        jedis.hmset(key, data);
        jedis.close();
    }

    @Override
    public HashMap<String, String> getHash(String key) {
        Jedis jedis = getJedisPool().getResource();
        jedis.select(redisDb);
        HashMap<String, String> data = (HashMap<String, String>) jedis.hgetAll(key);
        jedis.close();
        return data;
    }
}
