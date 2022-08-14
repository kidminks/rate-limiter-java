package com.rate.limiter.core.inter;

import java.util.HashMap;
import java.util.List;

public interface JedisService {
    void setHashSet(String key, HashMap<String, String> data);
    HashMap<String, String> getHashSet(String key);
    String runLua(String lua, List<String> keys, List<String> args);
}
