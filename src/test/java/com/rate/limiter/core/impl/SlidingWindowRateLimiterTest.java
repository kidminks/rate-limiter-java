package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.factory.RateLimiterFactory;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.model.enums.RateLimiterType;
import com.rate.limiter.utils.LimitDetailsConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class SlidingWindowRateLimiterTest {

    private Configuration configuration = new Configuration("localhost",6379,1,150,50, false);

    /**
     * Required local redis to test the flow completely
     * mocking is not being done because it is integral part and is based on scripts which will run in redis
     */
    @Test
    public void testSlidingWindowClass() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        LimitDetails limitDetails = new LimitDetails("test-sliding-1", 1L, 10000L);
        Boolean isLimitAvailable = abstractRateLimiter.check(limitDetails);
        Assert.assertEquals(isLimitAvailable, true);
        isLimitAvailable = abstractRateLimiter.check(limitDetails);
        Assert.assertEquals(isLimitAvailable, false);
    }

    /**
     * Async call allow only specific
     */
    @Test
    public void testSlidingWindowAsyncFunctionality() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        LimitDetails limitDetails = new LimitDetails("test-sliding-async-1", 50L, 60000L);
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

    /**
     * test with data stored
     */
    @Test
    public void testSlidingWindowWithData() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(LimitDetailsConstants.KEY, "test-with-data");
        data.put(LimitDetailsConstants.WINDOW, "10000");
        data.put(LimitDetailsConstants.MAX_REQUEST, "1");
        LimitDetails limitDetails = new LimitDetails(data);
        abstractRateLimiter.setLimitDetails(limitDetails);
        Boolean isRequestAvailable = abstractRateLimiter.check(limitDetails.getKey());
        Assert.assertEquals(Boolean.TRUE, isRequestAvailable);
        isRequestAvailable = abstractRateLimiter.check(limitDetails.getKey());
        Assert.assertEquals(isRequestAvailable, false);
        abstractRateLimiter.removeKey(limitDetails.getKey());
    }

    @Test
    public void testSlidingWindowAsyncWithData() {
        AbstractRateLimiter abstractRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
        HashMap<String, String> data = new HashMap<>();
        data.put(LimitDetailsConstants.KEY, "test-with-data-async");
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
                log.error(e.getLocalizedMessage());
            }
        }
        Assert.assertEquals(50, successCount);
        abstractRateLimiter.removeKey(limitDetails.getKey());
    }
}
