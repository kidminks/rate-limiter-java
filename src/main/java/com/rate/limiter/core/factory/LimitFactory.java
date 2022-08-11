package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.LimitServiceImpl;
import com.rate.limiter.core.inter.LimitService;

public class LimitFactory {
    public static LimitService getJedisService() {
        return new LimitServiceImpl();
    }
}
