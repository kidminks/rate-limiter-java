package io.github.kidminks.rate.limiter.utils;

public interface LimitDetailsConstants {
    String KEY = "key";
    String WINDOW = "window";
    String MAX_REQUEST = "max_request";
    String REDIS_LIMIT_SUCCESS = "0";
    Long MAX_REQUEST_SLIDING_WINDOW_LIMIT = 4000000001L;
}
