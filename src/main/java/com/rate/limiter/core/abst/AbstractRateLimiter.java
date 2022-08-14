package com.rate.limiter.core.abst;

import com.rate.limiter.core.errors.ObjectNotFoundError;
import com.rate.limiter.core.factory.JedisFactory;
import com.rate.limiter.core.inter.JedisService;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;

import java.util.Objects;

public abstract class AbstractRateLimiter {
   protected abstract Boolean isLimitLeft(LimitDetails limitDetails);

   private final JedisService jedisService;

   protected AbstractRateLimiter(Configuration configuration) {
      this.jedisService = JedisFactory.getJedisService(configuration.getRedisHost(),
              configuration.getRedisPort(), configuration.getRedisDb());
   }

   protected JedisService getJedisService() {
      if (Objects.isNull(jedisService)) {
         throw new ObjectNotFoundError(JedisService.class.getName());
      }
      return jedisService;
   }

   public Boolean check(LimitDetails limitDetails) {
       return isLimitLeft(limitDetails);
   }
}
