package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.factory.RateLimiterFactory;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.model.enums.RateLimiterType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class FixedWindowRateLimiterTest {
    private Configuration configuration = new Configuration("localhost",6379,1,150,50);

    @Test
    public void testFixedWindowClass() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.FIXED_WINDOW);
        LimitDetails limitDetails = new LimitDetails("test-fixed-1", 1L, 10000L);
        Boolean isLimitAvailable = abstractRateLimiter.check(limitDetails);
        Assert.assertEquals(isLimitAvailable, true);
        isLimitAvailable = abstractRateLimiter.check(limitDetails);
        Assert.assertEquals(isLimitAvailable, false);
    }

    @Test
    public void testFixedWindowAsyncFunctionality() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.FIXED_WINDOW);
        LimitDetails limitDetails = new LimitDetails("test-fixed-async-1", 50L, 60000L);
        List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>();
        for(int i=0;i<100;i++) {
            CompletableFuture<Boolean> futureTask = CompletableFuture.supplyAsync(() -> abstractRateLimiter.check(limitDetails));
            completableFutures.add(futureTask);
        }
        int successCount = 0;
        for (CompletableFuture<Boolean> completableFuture : completableFutures) {
            try {
                Boolean result = completableFuture.get();
                if (result.equals(Boolean.TRUE)) {
                    successCount += 1;
                }
            } catch (Exception e) {
                log.error(e.getLocalizedMessage());
            }
        }
        Assert.assertEquals(50, successCount);
    }
}
