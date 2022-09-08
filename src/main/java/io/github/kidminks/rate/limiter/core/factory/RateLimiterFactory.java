package io.github.kidminks.rate.limiter.core.factory;

import io.github.kidminks.rate.limiter.core.abst.AbstractRateLimiter;
import io.github.kidminks.rate.limiter.core.errors.WrongTypeException;
import io.github.kidminks.rate.limiter.core.impl.FixedWindowRateLimiter;
import io.github.kidminks.rate.limiter.core.impl.SlidingWindowRateLimiter;
import io.github.kidminks.rate.limiter.model.dto.Configuration;
import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;

public interface RateLimiterFactory {
    static AbstractRateLimiter getRateLimiter(Configuration configuration, RateLimiterType rateLimiterType) {
        switch (rateLimiterType) {
            case SLIDING_WINDOW: return SlidingWindowRateLimiter.getSlidingWindowRateLimiter(configuration);
            case FIXED_WINDOW: return FixedWindowRateLimiter.getFixedWindowRateLimiter(configuration);
            default: throw new WrongTypeException("RateLimiterFactory");
        }
    }
}
