# Rate Limiter Java Library


A Java library for rate limiting, assembled using extensible storage 
and application framework adaptors. 
The library's interfaces support thread-safe sync, async, 
and reactive usage patterns.

### Modules

RateLimiterJava provides the following stable pluggable modules

- Fixed Window rate limiter
- Sliding Window rate limiter

### Features

Configuration can be made to store the user data in redis using these modules.
Lua scripts is used in following cases to reduce redis calls

- Sliding Window rate limiter
- In case user data is handled using this library

### Installation

Add the dependency to your pom.xml file
```xml
<dependency>
    <groupId>io.github.kidminks</groupId>
    <artifactId>rate-limiter-java</artifactId>
    <version>0.0.1</version>
</dependency>
```

Then run from the root dir of the project:
```
mvn install
```

### Prerequisite

- jedis
- javax.validation
- guava
- lombok

## Examples

### Example Rate Limiter Service
Create a service RateLimiterService which will handle the library linking

```java
package com.example.demo;

import io.github.kidminks.rate.limiter.core.abst.AbstractRateLimiter;
import io.github.kidminks.rate.limiter.core.factory.RateLimiterFactory;
import io.github.kidminks.rate.limiter.model.dto.Configuration;
import io.github.kidminks.rate.limiter.model.dto.LimitDetails;
import io.github.kidminks.rate.limiter.model.enums.RateLimiterType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class RateLimiterService {

    private AbstractRateLimiter slidingWindowRateLimiter;
    private AbstractRateLimiter fixedWindowRateLimiter;

    private void initSlidingWindowRateLimiter() {
        Configuration configuration = Configuration.builder()
                .redisHost("localhost")
                .redisPort(6379)
                .redisDb(1)
                .maxTotal(10)
                .maxIdle(1)
                .useLuaScript(true)
                .build();
        this.slidingWindowRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.SLIDING_WINDOW);
    }

    private void initFixedWindowRateLimiter() {
        Configuration configuration = Configuration.builder()
                .redisHost("localhost")
                .redisPort(6379)
                .redisDb(1)
                .maxTotal(10)
                .maxIdle(1)
                .useLuaScript(false)
                .build();
        this.fixedWindowRateLimiter = RateLimiterFactory.getRateLimiter(configuration, RateLimiterType.FIXED_WINDOW);
    }

    public RateLimiterService() {
        initSlidingWindowRateLimiter();
        initFixedWindowRateLimiter();
    }

    public Boolean checkSlidingWindowLimit(String key) {
        return slidingWindowRateLimiter.check(new LimitDetails(key, 100L, 3600000L));
    }

    public Boolean checkFixedWindowLimit(String key) {
        return fixedWindowRateLimiter.check(new LimitDetails(key, 100L, 3600000L));
    }
}
```

### Sliding Window 
Checking limits left using sliding rate limiter api. 
These checks using lua scripts. 
Old request data are deleted with every request

```java
@RestController
@RequestMapping("/")
@Slf4j
public class RateLimiterTestController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/sliding-window")
    public void slidingWindowTest(@RequestHeader("X-AUTH-TOKEN") String token) {
        if(rateLimiterService.checkSlidingWindowLimit(token)) {
            log.info("limit available");
            return;
        }
        log.info("limit not available");
    }
}
```

### Fixed Window

increment function of redis is used to increase the key count.
Every time a new key is created expiry is set and that key is deleted after
the end.

```java
@RestController
@RequestMapping("/")
@Slf4j
public class RateLimiterTestController {

    @Autowired
    private RateLimiterService rateLimiterService;

    @GetMapping("/fixed-window")
    public void fixedWindowTest(@RequestHeader("X-AUTH-TOKEN") String token) {
        if(rateLimiterService.checkFixedWindowLimit(token)) {
            log.info("limit available");
            return;
        }
        log.info("limit not available");
    }
}
```

### Roadmap

|         **Feature**         | **Status** |
|:---------------------------:|:----------:|
| Sliding Window rate limiter | Released   |
| Fixed Window rate limiter   | Released   |
| Store user data             | Ongoing    |








