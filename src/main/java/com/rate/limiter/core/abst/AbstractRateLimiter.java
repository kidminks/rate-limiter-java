package com.rate.limiter.core.abst;

import com.rate.limiter.core.factory.JedisFactory;
import com.rate.limiter.core.inter.JedisService;
import com.rate.limiter.model.dto.Configuration;

public abstract class AbstractRateLimiter {
   protected abstract Boolean isLimitLeft(String id);
   protected abstract void resetLimit(String id);
   protected abstract void forceLimit(String id);
   protected abstract void disAllowFor(String id);
   protected abstract void skipFor(String id);

   private final JedisService jedisService;

   protected AbstractRateLimiter(Configuration configuration) {
      this.jedisService = JedisFactory.getJedisService(configuration.getRedisHost(),
              configuration.getRedisPort(), configuration.getRedisDb());
   }

   public Boolean check(String id) {
       return isLimitLeft(id);
   }
}
