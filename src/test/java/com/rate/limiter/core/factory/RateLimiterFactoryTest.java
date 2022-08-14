package com.rate.limiter.core.factory;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.impl.SlidingWindowRateLimiter;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.enums.RateLimiterType;
import org.junit.Assert;
import org.junit.Test;

public class RateLimiterFactoryTest {

    private Configuration configuration = new Configuration("localhost",6379,1,150,50,
            RateLimiterType.SLIDING_WINDOW, Boolean.FALSE);

    @Test
    public void testRateLimiterFactory() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        Assert.assertEquals(SlidingWindowRateLimiter.class, abstractRateLimiter.getClass());
    }
}
