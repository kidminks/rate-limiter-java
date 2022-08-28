package io.rate.limiter.model.dto;

import io.github.kidminks.rate.limiter.model.dto.Configuration;
import org.junit.Assert;
import org.junit.Test;

public class ConfigurationTest {

    @Test
    public void testConfiguration() {
        Configuration configuration = Configuration.builder()
                .maxIdle(50)
                .maxTotal(150)
                .redisDb(1)
                .redisHost("localhost")
                .redisPort(6379)
                .build();
        Assert.assertEquals(configuration.getRedisHost(), "localhost");
    }
}
