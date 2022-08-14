package com.rate.limiter.core.impl;

import com.rate.limiter.core.factory.JedisFactory;
import com.rate.limiter.core.inter.JedisService;
import com.rate.limiter.core.inter.RateLimitConfiguration;
import com.rate.limiter.model.dto.Configuration;

import java.util.Objects;

public class RateLimitConfigurationImpl implements RateLimitConfiguration {

    private JedisService jedisService;
    private Configuration configuration;

    private void initJedis() {
        jedisService = JedisFactory.getJedisService(configuration.getRedisHost(),
                configuration.getRedisPort(),
                configuration.getRedisDb());
    }

    public RateLimitConfigurationImpl(Configuration configuration) {
        this.configuration = configuration;
        initJedis();
    }

    @Override
    public void configureRateLimiter(Configuration configuration) {
        this.configuration = configuration;
        if (!Objects.isNull(jedisService)) {
            initJedis();
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public JedisService getJedisService() {
        return jedisService;
    }
}
