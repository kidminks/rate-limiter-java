package io.rate.limiter.model.enums;

import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;
import org.junit.Assert;
import org.junit.Test;

public class RateLimiterTypeTest {

    @Test
    public void testType() {
        Assert.assertEquals(RateLimiterType.SLIDING_WINDOW.name(), "SLIDING_WINDOW");
    }
}
