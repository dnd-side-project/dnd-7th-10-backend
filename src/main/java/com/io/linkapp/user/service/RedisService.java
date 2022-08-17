package com.io.linkapp.user.service;

import com.io.linkapp.common.RedisValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService{

    private final RedisTemplate<String, String> redisTemplate;

    public void setValues(String key, String data) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
    }

    public void setValues(String key, String data, Duration duration) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    public List<RedisValue> getAllValues() {
        Set<String> keys = redisTemplate.keys("*");
        List<RedisValue> redisValues = new ArrayList<>();
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        for (String key : keys) {
            redisValues.add(RedisValue.builder()
                    .key(key)
                    .value(values.get(key))
                    .build());
        }
        return redisValues;
    }

    public String getValues(String key) {
        log.info("Get Redis token");
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }
}
