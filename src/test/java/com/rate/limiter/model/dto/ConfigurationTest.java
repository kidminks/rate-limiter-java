package com.rate.limiter.model.dto;

import com.rate.limiter.model.enums.RateLimiterType;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testConfiguration() {
        Configuration configuration = new Configuration();
        Assert.assertEquals(configuration.getLimiterType(), RateLimiterType.SLIDING_WINDOW);
        Assert.assertEquals(configuration.getHandleLimitDetails(), Boolean.FALSE);
    }
}
