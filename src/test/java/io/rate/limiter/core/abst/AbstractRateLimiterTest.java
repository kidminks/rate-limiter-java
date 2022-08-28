package io.rate.limiter.core.abst;

import io.github.kidminks.rate.limiter.core.abst.AbstractRateLimiter;
import io.github.kidminks.rate.limiter.core.factory.RateLimiterFactory;
import io.github.kidminks.rate.limiter.model.dto.Configuration;
import io.github.kidminks.rate.limiter.model.dto.LimitDetails;
import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;
import io.github.kidminks.rate.limiter.utils.LimitDetailsConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class AbstractRateLimiterTest {
    private Configuration configuration = new Configuration("localhost",6379,1,150,50);

    @Test
    public void limitDetailsInCache() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(LimitDetailsConstants.KEY, "test-key");
        data.put(LimitDetailsConstants.WINDOW, "0");
        data.put(LimitDetailsConstants.MAX_REQUEST, "0");
        LimitDetails limitDetails = new LimitDetails(data);
        abstractRateLimiter.setLimitDetails(limitDetails);
        LimitDetails limitDetails1 = abstractRateLimiter.getLimitDetails(limitDetails.getKey());
        abstractRateLimiter.removeKey(limitDetails.getKey());
        Assert.assertEquals(limitDetails.getKey(), limitDetails1.getKey());
    }
}
