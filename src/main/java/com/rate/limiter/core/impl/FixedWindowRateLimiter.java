package com.rate.limiter.core.impl;

import com.rate.limiter.core.abst.AbstractRateLimiter;
import com.rate.limiter.core.errors.ObjectNotFoundError;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.utils.LimitDetailsConstants;
import com.rate.limiter.utils.LuaScripts;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FixedWindowRateLimiter extends AbstractRateLimiter {

    private static FixedWindowRateLimiter fixedWindowRateLimiter;

    private FixedWindowRateLimiter(Configuration configuration) {
        super(configuration);
    }

    /**
     * fixed rate limiter the entry is set on the first api hit and a window is given so if we want user to
     * use 100 request in one one hour he can use all in 59th minute and other hundred in 1 minute of next hour
     * @param limitDetails
     * @return
     */
    @Override
    protected Boolean isLimitLeft(@Valid LimitDetails limitDetails) {
        Long request = getJedisService().increment(limitDetails.getKey(), limitDetails.getWindow());
        return request <= limitDetails.getMaxRequest();
    }

    /**
     * get is call correct in case of only keys
     * @param key
     * @return
     */
    @Override
    protected Boolean isLimitLeft(String key) {
        List<String> keys = Arrays.asList(key+"_limit", key);
        String resp = getJedisService().runLua(LuaScripts.fixedRateLimiterWithStorage(), keys, new ArrayList<>());
        if ("-1".equals(resp)) {
            throw new ObjectNotFoundError(key + " missing");
        }
        return resp.equals(LimitDetailsConstants.REDIS_LIMIT_SUCCESS);
    }

    public static FixedWindowRateLimiter getFixedWindowRateLimiter(Configuration configuration) {
        if (Objects.isNull(fixedWindowRateLimiter)) {
            synchronized(FixedWindowRateLimiter.class) {
                if (Objects.isNull(fixedWindowRateLimiter)) {
                    fixedWindowRateLimiter = new FixedWindowRateLimiter(configuration);
                }
            }
        }
        return fixedWindowRateLimiter;
    }
}
