package com.rate.limiter.core.inter;

import java.util.HashMap;
import java.util.List;

public interface JedisService {
    String runLua(String lua, List<String> keys, List<String> args);
    Long increment(String key, Long window);
    void deleteKey(String key);
    void hashSet(String key, HashMap<String, String> data);
    HashMap<String, String> getHash(String key);
}
