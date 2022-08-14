package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.JedisServiceImpl;
import com.rate.limiter.core.inter.JedisService;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.JedisPoolConfig;

public class JedisFactoryTest {
    @Test
    public void testJedisFactory() {
        JedisService jedisService = JedisFactory.getJedisService("", 6790,  0, new JedisPoolConfig());
        Assert.assertEquals(JedisServiceImpl.class, jedisService.getClass());
    }
}
