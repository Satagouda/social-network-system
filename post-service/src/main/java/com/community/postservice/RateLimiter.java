package com.community.postservice;

import com.community.commonlib.exception.GlobalException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {

    private final Map<String, Integer> requestCount = new ConcurrentHashMap<>();
    private final Map<String, Long> timestamp = new ConcurrentHashMap<>();

    private static final int LIMIT = 5; // max 5 requests
    private static final long WINDOW = 60000; // 1 min

    public void validate(String userId) {

        long now = System.currentTimeMillis();

        timestamp.putIfAbsent(userId, now);
        requestCount.putIfAbsent(userId, 0);

        if (now - timestamp.get(userId) > WINDOW) {
            requestCount.put(userId, 0);
            timestamp.put(userId, now);
        }

        if (requestCount.get(userId) >= LIMIT) {
            throw new GlobalException("Rate limit exceeded");
        }

        requestCount.put(userId, requestCount.get(userId) + 1);
    }
}
