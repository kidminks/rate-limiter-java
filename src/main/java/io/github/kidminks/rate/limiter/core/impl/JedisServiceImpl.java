package io.github.kidminks.rate.limiter.core.impl;

import io.github.kidminks.rate.limiter.core.errors.UnImplementedError;
import io.github.kidminks.rate.limiter.core.inter.JedisService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.*;

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

    @Override
    public void zRemByScorePipeline(Pipeline pipeline, String key, Double min, Double max) {
        pipeline.zremrangeByScore(key, min, max);
    }

    @Override
    public void zCard(Pipeline pipeline, String key) {
        pipeline.zcard(key);
    }

    @Override
    public void zAdd(Pipeline pipeline, String key, Double score, String value) {
        pipeline.zadd(key, score, value);
    }

    @Override
    public void expire(Pipeline pipeline, String key, Long after) {
        pipeline.expire(key, after);
    }

    @Override
    public List<Object> runPipeline(Map<String, List<Object>> pipelineFunction) {
        Jedis jedis = getJedisPool().getResource();
        Pipeline pipeline = jedis.pipelined();
        for (Map.Entry<String, List<Object>> func : pipelineFunction.entrySet()) {
            switch (func.getKey()) {
                case "zRemByScorePipeline": {
                    List<Object> args = func.getValue();
                    zRemByScorePipeline(pipeline, args.get(0).toString(),
                            Double.parseDouble(args.get(1).toString()), Double.parseDouble(args.get(2).toString()));
                } break;
                case "zCard": {
                    List<Object> args = func.getValue();
                    zCard(pipeline, args.get(0).toString());
                } break;
                case "zAdd": {
                    List<Object> args = func.getValue();
                    zAdd(pipeline, args.get(0).toString(),
                            Double.parseDouble(args.get(1).toString()), args.get(2).toString());
                } break;
                case "expire": {
                    List<Object> args = func.getValue();
                    expire(pipeline, args.get(0).toString(), Long.parseLong(args.get(1).toString()));
                } break;
                default: throw new UnImplementedError("function not implemented");
            }
        }
        jedis.close();
        return pipeline.syncAndReturnAll();
    }
}
