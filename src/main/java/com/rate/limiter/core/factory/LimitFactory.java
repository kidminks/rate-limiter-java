package com.rate.limiter.core.factory;

import com.rate.limiter.core.impl.LimitServiceImpl;
import com.rate.limiter.core.inter.LimitService;

public interface LimitFactory {
    static LimitService getJedisService() {
        return new LimitServiceImpl();
    }
}
