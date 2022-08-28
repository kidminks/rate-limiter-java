package io.github.kidminks.rate.limiter.utils;

public interface LuaScripts {
    static String slidingRateLimiter() {
        return  "            local current_time = redis.call('TIME')\n" +
                "            local trim_time = tonumber(current_time[1]) - ARGV[2]\n" +
                "            redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, trim_time)\n" +
                "            local request_count = redis.call('ZCARD',KEYS[1])\n" +
                "\n" +
                "            if request_count < tonumber(ARGV[1]) then\n" +
                "                redis.call('ZADD', KEYS[1], current_time[1], current_time[1] .. current_time[2])\n" +
                "                redis.call('EXPIRE', KEYS[1], ARGV[3])\n" +
                "                return 0\n" +
                "            end\n" +
                "            return 1\n";
    }

    static String slidingRateLimiterWithStorage() {
        return  "            local key_data = redis.call('HMGET', KEYS[2], 'window', 'max_request')\n" +
                "            if key_data[1] == nil or key_data[2] == nil then\n return -1\nend\n" +
                "            local current_time = redis.call('TIME')\n" +
                "            local trim_time = tonumber(current_time[1]) - tonumber(key_data[1])\n" +
                "            redis.call('ZREMRANGEBYSCORE', KEYS[1], 0, trim_time)\n" +
                "            local request_count = redis.call('ZCARD',KEYS[1])\n" +
                "            local time_out = tonumber(key_data[1])/1000 + 2 \n" +
                "            if request_count < tonumber(key_data[2]) then\n" +
                "                redis.call('ZADD', KEYS[1], current_time[1], current_time[1] .. current_time[2])\n" +
                "                redis.call('EXPIRE', KEYS[1], time_out)\n" +
                "                return 0\n" +
                "            end\n" +
                "            return 1\n";
    }

    static String fixedRateLimiterWithStorage() {
        return  "            local key_data = redis.call('HMGET', KEYS[2], 'window', 'max_request')\n" +
                "            if key_data[1] == nil or key_data[2] == nil then\n return -1\nend\n" +
                "            local request_count = redis.call('INCR', KEYS[1])\n" +
                "            if request_count <= tonumber(key_data[2]) then\n" +
                "                if(request_count == 1) then\nredis.call('EXPIRE', KEYS[1], key_data[2])\nend\n"+
                "                return 0\n" +
                "            end\n" +
                "            return 1\n";
    }
}
