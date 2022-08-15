package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;

import javax.validation.Valid;
import java.util.Objects;

public class FixedWindowRateLimiter extends AbstractRateLimiter {

    private static FixedWindowRateLimiter fixedWindowRateLimiter;

    private FixedWindowRateLimiter(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Boolean isLimitLeft(@Valid LimitDetails limitDetails) {
        Long request = getJedisService().increment(limitDetails.getId(), limitDetails.getWindow());
        if (request <= limitDetails.getMaxRequest()) {
            return true;
        }
        return false;
    }

    public static FixedWindowRateLimiter getFixedWindowRateLimiter(Configuration configuration) {
        if (Objects.isNull(fixedWindowRateLimiter)) {
            synchronized(FixedWindowRateLimiter.class) {
                if (Objects.isNull(fixedWindowRateLimiter)) {
                    fixedWindowRateLimiter = new FixedWindowRateLimiter(configuration);
                }
            }
        }
        return fixedWindowRateLimiter;
    }
}
