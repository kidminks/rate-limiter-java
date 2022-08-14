package com.rate.limiter.core.abst;

import com.rate.limiter.core.errors.ObjectNotFoundError;
import com.rate.limiter.core.factory.JedisFactory;
import com.rate.limiter.core.inter.JedisService;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.Valid;
import java.util.Objects;

public abstract class AbstractRateLimiter {
   /**
    * Check if api limit is available for the id provided in limit details
    * @param limitDetails
    * @return
    */
   protected abstract Boolean isLimitLeft(LimitDetails limitDetails);

   private final JedisService jedisService;

   protected AbstractRateLimiter(Configuration configuration) {
      JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
      jedisPoolConfig.setMaxTotal(configuration.getMaxTotal());
      jedisPoolConfig.setMaxIdle(configuration.getMaxIdle());
      this.jedisService = JedisFactory.getJedisService(configuration.getRedisHost(),
              configuration.getRedisPort(), configuration.getRedisDb(), jedisPoolConfig);
   }

   protected JedisService getJedisService() {
      if (Objects.isNull(jedisService)) {
         throw new ObjectNotFoundError(JedisService.class.getName());
      }
      return jedisService;
   }

   /**
    * This function can be called by all the child methods which inturns
    * calculate the limit and returns the data
    * @param limitDetails
    * @return
    */
   public Boolean check(@Valid LimitDetails limitDetails) {
       return isLimitLeft(limitDetails);
   }
}
