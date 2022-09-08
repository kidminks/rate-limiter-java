package io.rate.limiter.core.factory;

import io.github.kidminks.rate.limiter.core.abst.AbstractRateLimiter;
import io.github.kidminks.rate.limiter.core.factory.RateLimiterFactory;
import io.github.kidminks.rate.limiter.core.impl.FixedWindowRateLimiter;
import io.github.kidminks.rate.limiter.core.impl.SlidingWindowRateLimiter;
import io.github.kidminks.rate.limiter.model.dto.Configuration;
import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;
import org.junit.Assert;
import org.junit.Test;

public class RateLimiterFactoryTest {

    private Configuration configuration = new Configuration("localhost",6379,1,150,50,true);

    @Test
    public void testRateLimiterFactory() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        Assert.assertEquals(SlidingWindowRateLimiter.class, abstractRateLimiter.getClass());

        abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.FIXED_WINDOW);
        Assert.assertEquals(FixedWindowRateLimiter.class, abstractRateLimiter.getClass());
    }
}
