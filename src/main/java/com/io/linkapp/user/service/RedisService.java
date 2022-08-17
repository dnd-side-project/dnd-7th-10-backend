package com.io.linkapp.user.service;

import com.io.linkapp.common.RedisValue;
import com.io.linkapp.config.security.jwt.JwtProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService{

    private final RedisTemplate<String, String> redisTemplate;

    public void setValues(String key, String data) {
        log.info("Set Redis Token");
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, data);
        redisTemplate.expire(key, 3, TimeUnit.DAYS);
    }


    public String getValues(String key) {
        log.info("Get Redis token");
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        return values.get(key);
    }

    public List<RedisValue> getAllValues() {
        Set<String> keys = redisTemplate.keys("*");
        List<RedisValue> redisValues = new ArrayList<>();
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        for (String key : keys) {
            Long expireTime = redisTemplate.getExpire(key);
            redisValues.add(RedisValue.builder()
                    .key(key)
                    .value(values.get(key))
                    .expireTime(String.valueOf(expireTime))
                    .build());
        }
        return redisValues;
    }
}
