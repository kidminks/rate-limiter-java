package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.JedisServiceImpl;
import com.rate.limiter.core.inter.JedisService;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactoryTest {
    @Test
    public void testJedisFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(150);
        jedisPoolConfig.setMaxIdle(50);
        JedisService jedisService = JedisFactory.getJedisService("localhost", 6379,  1, jedisPoolConfig);
        Assert.assertEquals(JedisServiceImpl.class, jedisService.getClass());
    }
}
