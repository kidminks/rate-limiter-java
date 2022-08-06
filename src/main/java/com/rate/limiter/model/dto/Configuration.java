package com.rate.limiter.model.dto;

import com.rate.limiter.model.enums.RateLimiterType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Configuration {
    private String redisHost;
    private Integer redisPort;
    private Integer redisDb;

    private RateLimiterType limiterType = RateLimiterType.SLIDING_WINDOW;
}
