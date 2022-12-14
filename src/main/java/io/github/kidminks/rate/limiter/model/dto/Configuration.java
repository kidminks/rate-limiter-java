package io.github.kidminks.rate.limiter.model.dto;

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
    private Integer maxTotal;
    private Integer maxIdle;
    private Boolean useLuaScript = Boolean.FALSE;
}
