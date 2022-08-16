package com.rate.limiter.core.abst;

import com.rate.limiter.core.errors.ObjectNotFoundError;
import com.rate.limiter.core.factory.JedisFactory;
import com.rate.limiter.core.inter.JedisService;
import com.rate.limiter.model.dto.Configuration;
import com.rate.limiter.model.dto.LimitDetails;
import com.rate.limiter.utils.LimitDetailsConstants;
import redis.clients.jedis.JedisPoolConfig;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractRateLimiter {
   /**
    * Check if api limit is available for the id provided in limit details
    * @param limitDetails
    * @return
    */
   protected abstract Boolean isLimitLeft(LimitDetails limitDetails);

   protected abstract Boolean isLimitLeft(String key);

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
    * Setting key details in cache to be accessed later
    * @param limitDetails
    */
   public void setLimitDetails(@Valid LimitDetails limitDetails) {
      HashMap<String, String> data = new HashMap<>();
      data.put(LimitDetailsConstants.KEY, limitDetails.getKey());
      data.put(LimitDetailsConstants.WINDOW, Long.toString(limitDetails.getWindow()));
      data.put(LimitDetailsConstants.MAX_REQUEST, Long.toString(limitDetails.getMaxRequest()));
      getJedisService().hashSet(limitDetails.getKey(), data);
   }

   /**
    * Get the key details
    * @param key
    * @return
    */
   public LimitDetails getLimitDetails(String key) {
      HashMap<String, String> data = getJedisService().getHash(key);
      return new LimitDetails(data);
   }

   /**
    * Remove key manually in case of storage
    * @param key
    */
   public  void removeKey(String key) {
      getJedisService().deleteKey(key);
   }

   /**
    * This function can be called by all the child methods which in turns
    * calculate the limit and returns the data
    * @param limitDetails
    * @return
    */
   public Boolean check(@Valid LimitDetails limitDetails) {
       return isLimitLeft(limitDetails);
   }

   /**
    * function to check data with stored details
    * @param key
    * @return
    */
   public Boolean check(String key) {
      return isLimitLeft(key);
   }

   /**
    * store and check this can be used to fill key details in data source if its not present
    * @param limitDetails
    * @return
    */
   public Boolean storeAndCheck(@Valid LimitDetails limitDetails) {
      setLimitDetails(limitDetails);
      return check(limitDetails.getKey());
   }
}
