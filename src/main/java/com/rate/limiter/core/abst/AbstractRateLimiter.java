package com.rate.limiter.core.abst;

public abstract class AbstractRateLimiter {
   abstract Boolean isLimitLeft(String id);
   abstract void resetLimit(String id);
   abstract void forceLimit(String id);
   abstract void disAllowFor(String id);
   abstract void skipFor(String id);

   public Boolean check(String id) {
       return isLimitLeft(id);
   }
}
