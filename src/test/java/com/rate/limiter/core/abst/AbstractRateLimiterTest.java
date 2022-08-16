package com.rate.limiter.core.abst;

import com.rate.limiter.core.factory.RateLimiterFactory;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.KeyDetails;
import com.rate.limiter.model.enums.RateLimiterType;
import com.rate.limiter.utils.KeyDetailsConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class AbstractRateLimiterTest {
    private Configuration configuration = new Configuration("localhost",6379,1,150,50, false);

    @Test
    public void keyDetailsInCache() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(KeyDetailsConstants.KEY, "test-key");
        data.put(KeyDetailsConstants.WINDOW, "0");
        data.put(KeyDetailsConstants.MAX_REQUEST, "0");
        KeyDetails keyDetails = new KeyDetails(data);
        abstractRateLimiter.setKeyDetails(keyDetails);
        KeyDetails keyDetails1 = abstractRateLimiter.getKeyDetails(keyDetails.getKey());
        Assert.assertEquals(keyDetails.getKey(), keyDetails1.getKey());
        abstractRateLimiter.removeKey(keyDetails.getKey());
    }
}
