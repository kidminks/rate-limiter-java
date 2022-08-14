package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.utils.LimitDetailsConstants;
import com.rate.limiter.utils.LuaScripts;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SlidingWindowRateLimiter extends AbstractRateLimiter {

    private static SlidingWindowRateLimiter slidingWindowRateLimiter;

    private SlidingWindowRateLimiter(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected Boolean isLimitLeft(@Valid LimitDetails limitDetails) {
        List<String> keys = Collections.singletonList(limitDetails.getId());
        List<String> args = Arrays.asList(limitDetails.getMaxRequest().toString(), limitDetails.getWindow().toString());
        String resp = getJedisService().runLua(LuaScripts.slidingRateLimiter(), keys, args);
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