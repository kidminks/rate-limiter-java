package com.rate.limiter.utils;

public interface LuaScripts {
    static String slidingRateLimiter() {
        return "\"\n" +
                "            local current_time = redis.call('TIME')\n" +
                "            local trim_time = tonumber(current_time[1]) - ARGV[2]\n" +
                "            redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, trim_time)\n" +
                "            local request_count = redis.call('ZCARD',KEYS[1])\n" +
                "\n" +
                "            if request_count < tonumber(ARGV[1]) then\n" +
                "                redis.call('ZADD', KEY[1], current_time[1], current_time[1] .. current_time[2])\n" +
                "                redis.call('EXPIRE', KEYS[1], ARGV[2])\n" +
                "                return 0\n" +
                "            end\n" +
                "            return 1\n" +
                "            \"";
    }
}
