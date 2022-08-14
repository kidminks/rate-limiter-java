package com.rate.limiter.core.inter;

import com.rate.limiter.model.dto.Configuration;

public interface RateLimitConfiguration {
    void configureRateLimiter(Configuration configuration);
    JedisService getJedisService();
    Configuration getConfiguration();
}
