package io.github.kidminks.rate.limiter.core.inter;

import io.github.kidminks.rate.limiter.model.dto.Configuration;

public interface RateLimitConfiguration {
    void configureRateLimiter(Configuration configuration);
    JedisService getJedisService();
    Configuration getConfiguration();
}
