package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.errors.ObjectNotFoundError;
import com.rate.limiter.core.errors.UnProcessableError;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.utils.LimitDetailsConstants;
import com.rate.limiter.utils.LuaScripts;

import javax.validation.Valid;
import java.util.*;

public class SlidingWindowRateLimiter extends AbstractRateLimiter {

    private static SlidingWindowRateLimiter slidingWindowRateLimiter;

    private SlidingWindowRateLimiter(Configuration configuration) {
        super(configuration);
    }

    /**
     * SlidingWindowRateLimiter will store all the access in redis set with currentTime as score
     * it deletes all the old time so that we can get exact numbers of request user had made in (current_time - window)
     * it adds the new time to set if the request is success
     * @param limitDetails
     * @return
     */
    @Override
    protected Boolean isLimitLeft(@Valid LimitDetails limitDetails) {
        if (limitDetails.getMaxRequest() >= LimitDetailsConstants.MAX_REQUEST_SLIDING_WINDOW_LIMIT) {
            throw new UnProcessableError("large max request which should be less than " +
                    LimitDetailsConstants.MAX_REQUEST_SLIDING_WINDOW_LIMIT);
        }
        List<String> keys = Collections.singletonList(limitDetails.getLimitKey());
        long keyExpiry = (limitDetails.getWindow() / 1000) + 2;
        List<String> args = Arrays.asList(limitDetails.getMaxRequest().toString(),
                limitDetails.getWindow().toString(), Long.toString(keyExpiry));
        String resp = getJedisService().runLua(LuaScripts.slidingRateLimiter(), keys, args);
        return resp.equals(LimitDetailsConstants.REDIS_LIMIT_SUCCESS);
    }

    /**
     * Sliding window with stored data
     * @param key
     * @return
     */
    @Override
    protected Boolean isLimitLeft(String key) {
        List<String> keys = Arrays.asList(key+"_limit", key);
        String resp = getJedisService().runLua(LuaScripts.slidingRateLimiterWithStorage(), keys, new ArrayList<>());
        if ("-1".equals(resp)) {
            throw new ObjectNotFoundError(key + " missing");
        }
        return resp.equals(LimitDetailsConstants.REDIS_LIMIT_SUCCESS);
    }

    public static SlidingWindowRateLimiter getSlidingWindowRateLimiter(Configuration configuration) {
        if (Objects.isNull(slidingWindowRateLimiter)) {
            synchronized(SlidingWindowRateLimiter.class) {
                if (Objects.isNull(slidingWindowRateLimiter)) {
                    slidingWindowRateLimiter = new SlidingWindowRateLimiter(configuration);
                }
            }
        }
        return slidingWindowRateLimiter;
    }
}
