package com.rate.limiter.core.factory;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.errors.WrongTypeException;
import com.rate.limiter.core.impl.FixedWindowRateLimiter;
import com.rate.limiter.core.impl.SlidingWindowRateLimiter;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.enums.RateLimiterType;

public interface RateLimiterFactory {
    static AbstractRateLimiter getRateLimiter(Configuration configuration, RateLimiterType rateLimiterType) {
        switch (rateLimiterType) {
            case SLIDING_WINDOW: return SlidingWindowRateLimiter.getSlidingWindowRateLimiter(configuration);
            case FIXED_WINDOW: return FixedWindowRateLimiter.getFixedWindowRateLimiter(configuration);
            default: throw new WrongTypeException("RateLimiterFactory");
        }
    }
}
