package com.rate.limiter.core.inter;

import java.util.List;

public interface JedisService {
    String runLua(String lua, List<String> keys, List<String> args);
}
