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

    /**
     * fixed rate limiter the entry is set on the first api hit and a window is given so if we want user to
     * use 100 request in one one hour he can use all in 59th minute and other hundred in 1 minute of next hour
     * @param limitDetails
     * @return
     */
    @Override
    protected Boolean isLimitLeft(@Valid LimitDetails limitDetails) {
        Long request = getJedisService().increment(limitDetails.getId(), limitDetails.getWindow());
        return request <= limitDetails.getMaxRequest();
    }

    @Override
    protected Boolean isLimitLeft(String key) {
        return null;
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
