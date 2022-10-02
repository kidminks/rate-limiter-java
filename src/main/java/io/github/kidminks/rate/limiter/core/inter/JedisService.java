package io.github.kidminks.rate.limiter.core.inter;

import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface JedisService {
    String runLua(String lua, List<String> keys, List<String> args);
    Long increment(String key, Long window);
    void deleteKey(String key);
    void hashSet(String key, HashMap<String, String> data);
    HashMap<String, String> getHash(String key);
    void zRemByScorePipeline(Pipeline pipeline, String key, Double min, Double max);
    void zCard(Pipeline pipeline, String key);
    void zAdd(Pipeline pipeline, String key, Double score, String value);
    void expire(Pipeline pipeline, String key, Long after);
    List<Object> runPipeline(Map<String, List<Object>> pipelineFunction);
}
