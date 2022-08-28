package io.rate.limiter.core.impl;

import io.github.kidminks.rate.limiter.core.abst.AbstractRateLimiter;
import io.github.kidminks.rate.limiter.core.factory.RateLimiterFactory;
import io.github.kidminks.rate.limiter.model.dto.Configuration;
import io.github.kidminks.rate.limiter.model.dto.LimitDetails;
import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;
import io.github.kidminks.rate.limiter.utils.LimitDetailsConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FixedWindowRateLimiterTest {
    private Configuration configuration = new Configuration("localhost",6379,1,
            150,50);

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
                e.printStackTrace();
            }
        }
        Assert.assertEquals(50, successCount);
    }

    @Test
    public void testFixedWindowWithData() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.FIXED_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(LimitDetailsConstants.KEY, "test-fixed-with-data");
        data.put(LimitDetailsConstants.WINDOW, "10000");
        data.put(LimitDetailsConstants.MAX_REQUEST, "1");
        LimitDetails limitDetails = new LimitDetails(data);
        abstractRateLimiter.setLimitDetails(limitDetails);
        Boolean isLimitAvailable = abstractRateLimiter.check(limitDetails.getKey());
        Assert.assertEquals(isLimitAvailable, true);
        isLimitAvailable = abstractRateLimiter.check(limitDetails.getKey());
        Assert.assertEquals(isLimitAvailable, false);
        abstractRateLimiter.removeKey(limitDetails.getKey());
    }

    @Test
    public void testFixedWindowAsyncWithData() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(LimitDetailsConstants.KEY, "test-fixed-with-data-async");
        data.put(LimitDetailsConstants.WINDOW, "60000");
        data.put(LimitDetailsConstants.MAX_REQUEST, "50");
        LimitDetails limitDetails = new LimitDetails(data);
        abstractRateLimiter.setLimitDetails(limitDetails);
        List<CompletableFuture<Boolean>> completableFutures = new ArrayList<>();
        for(int i=0;i<100;i++) {
            CompletableFuture<Boolean> futureTask = CompletableFuture.supplyAsync(() -> abstractRateLimiter.check(limitDetails.getKey()));
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
                e.printStackTrace();
            }
        }
        Assert.assertEquals(50, successCount);
        abstractRateLimiter.removeKey(limitDetails.getKey());
    }
}
