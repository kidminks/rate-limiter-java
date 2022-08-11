package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.model.dto.Configuration;

import java.util.Objects;

public class SlidingWindowRateLimiter extends AbstractRateLimiter {

    private static SlidingWindowRateLimiter slidingWindowRateLimiter;

    private SlidingWindowRateLimiter(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected void resetLimit(String id) {

    }

    @Override
    protected void forceLimit(String id) {

    }

    @Override
    protected void disAllowFor(String id) {

    }

    @Override
    protected void skipFor(String id) {

    }

    @Override
    protected Boolean isLimitLeft(String id) {
        return null;
    }

    public static SlidingWindowRateLimiter getSlidingWindowRateLimiter(Configuration configuration) {
        if (Objects.isNull(slidingWindowRateLimiter)) {
            synchronized(SlidingWindowRateLimiter.class) {
                if (Objects.isNull(slidingWindowRateLimiter)) {
                    slidingWindowRateLimiter = new SlidingWindowRateLimiter(configuration);
                }
            }
        }
        return slidingWindowRateLimiter;
    }
}
